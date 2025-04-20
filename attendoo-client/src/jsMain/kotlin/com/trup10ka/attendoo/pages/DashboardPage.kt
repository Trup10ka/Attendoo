package com.trup10ka.attendoo.pages

import com.trup10ka.attendoo.FULL_ALL_ATTENDANCES_ENDPOINT
import com.trup10ka.attendoo.AttendooPageManager
import com.trup10ka.attendoo.DEPARTMENT_JSON_FIELD
import com.trup10ka.attendoo.FULL_ALL_USERS_ENDPOINT
import com.trup10ka.attendoo.FULL_VERIFY_ENDPOINT
import com.trup10ka.attendoo.TOKEN_NAME
import com.trup10ka.attendoo.USERNAME_JSON_FIELD
import com.trup10ka.attendoo.data.User
import com.trup10ka.attendoo.dto.UserAttendanceDTO
import com.trup10ka.attendoo.dto.UserAttendanceWithInfoDTO
import com.trup10ka.attendoo.fetch.HttpClient
import com.trup10ka.attendoo.pages.builders.DashboardPageBuilderImp
import com.trup10ka.attendoo.pages.constant.PageType
import com.trup10ka.attendoo.util.launchDefaultCoroutine
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import kotlinx.browser.localStorage
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

class DashboardPage(
    override val pageType: PageType,
    override val pageManager: AttendooPageManager,
    private val httpClient: HttpClient
) : Page
{
    override val pageBuilder = DashboardPageBuilderImp()
    private var currentUser: User? = null
    private var currentUserDepartment: String? = null

    override fun init()
    {
        fetchCurrentUserInfo()
    }

    override fun show()
    {
        // Build the attendance box (container)
        pageBuilder.buildAttendanceBox()

        // Fetch current user info, then users and attendances
        fetchCurrentUserInfo()
    }

    private fun fetchCurrentUserInfo()
    {
        // Get user info from server using the verify endpoint
        val token = localStorage.getItem(TOKEN_NAME) ?: ""
        if (token.isNotEmpty())
        {
            launchDefaultCoroutine {
                try
                {
                    // Call the verify endpoint to get user info
                    val response = httpClient.getVia(FULL_VERIFY_ENDPOINT) as HttpResponse

                    if (response.status.value == 200)
                    {
                        val responseText = response.bodyAsText()
                        val jsonResponse = Json.parseToJsonElement(responseText).jsonObject

                        // Extract username and department from response
                        val username = jsonResponse[USERNAME_JSON_FIELD]?.jsonPrimitive?.content
                        currentUserDepartment = jsonResponse[DEPARTMENT_JSON_FIELD]?.jsonPrimitive?.content

                        // Now fetch users and attendances
                        fetchUsers(username)
                        fetchAttendances()
                    }
                    else
                    {
                        console.error("Error verifying token: ${response.status}")
                        fetchUsers(null)
                        fetchAttendances()
                    }
                }
                catch (e: Exception)
                {
                    console.error("Error verifying token: ${e.message}")
                    fetchUsers(null)
                    fetchAttendances()
                }
            }
        }
        else
        {
            fetchUsers(null)
            fetchAttendances()
        }
    }

    private fun fetchUsers(currentUsername: String?)
    {
        launchDefaultCoroutine {
            try
            {
                val fetchedUsers = httpClient.getVia(FULL_ALL_USERS_ENDPOINT) as HttpResponse

                // Parse the response
                val responseText = fetchedUsers.bodyAsText()
                val allUsers: List<User> = Json.decodeFromString(responseText)

                // Find current user
                currentUser = if (currentUsername != null)
                {
                    allUsers.find { it.attendooUsername == currentUsername }
                }
                else
                {
                    null
                }

                // Filter users by department
                val departmentUsers = if (currentUserDepartment != null)
                {
                    allUsers.filter { it.userDepartment == currentUserDepartment }
                }
                else
                {
                    allUsers
                }

                // Build the department users section
                pageBuilder.buildDepartmentUsers(currentUser, departmentUsers)
            }
            catch (e: Exception)
            {
                // Handle error
                console.error("Error fetching users: ${e.message}")

                // Show empty user list
                pageBuilder.buildDepartmentUsers(null, emptyList())
            }
        }
    }

    private fun fetchAttendances()
    {
        launchDefaultCoroutine {
            try
            {
                val fetchedAttendances = httpClient.getVia(FULL_ALL_ATTENDANCES_ENDPOINT) as HttpResponse

                // Parse the response
                val responseText = fetchedAttendances.bodyAsText()
                val allAttendances: List<UserAttendanceWithInfoDTO> = Json.decodeFromString(responseText)

                // Fetch all users to map user IDs to user objects
                val fetchedUsers = httpClient.getVia(FULL_ALL_USERS_ENDPOINT) as HttpResponse
                val usersResponseText = fetchedUsers.bodyAsText()
                val allUsers: List<User> = Json.decodeFromString(usersResponseText)

                // Build the dynamic attendances section with attendances from the server
                // (Server now filters attendances by department)
                pageBuilder.buildDynamicAttendances(allAttendances, allUsers)
            }
            catch (e: Exception)
            {
                // Handle error
                console.error("Error fetching attendances: ${e.message}")

                // Show empty attendances list
                pageBuilder.buildDynamicAttendances(emptyList(), emptyList())
            }
        }
    }

    override fun hide()
    {
        pageBuilder.eraseDynamicElement()
    }
}
