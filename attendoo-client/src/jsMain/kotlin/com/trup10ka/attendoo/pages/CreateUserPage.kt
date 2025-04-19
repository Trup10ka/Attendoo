package com.trup10ka.attendoo.pages

import com.trup10ka.attendoo.AttendooPageManager
import com.trup10ka.attendoo.FULL_ALL_DEPARTMENTS_ENDPOINT
import com.trup10ka.attendoo.FULL_ALL_ROLES_ENDPOINT
import com.trup10ka.attendoo.FULL_REGISTER_ENDPOINT
import com.trup10ka.attendoo.dto.DepartmentDTO
import com.trup10ka.attendoo.dto.RoleDTO
import com.trup10ka.attendoo.dto.UserDTO
import com.trup10ka.attendoo.fetch.KtorHttpClient
import com.trup10ka.attendoo.pages.builders.CreateUserPageBuilderImp
import com.trup10ka.attendoo.pages.constant.PageType
import com.trup10ka.attendoo.util.getDivByID
import com.trup10ka.attendoo.util.getButtonByID
import com.trup10ka.attendoo.util.getInputByID
import com.trup10ka.attendoo.util.getSelectById
import com.trup10ka.attendoo.pages.constant.ElementID.*
import com.trup10ka.attendoo.util.stylesOf
import com.trup10ka.attendoo.util.launchDefaultCoroutine
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import kotlinx.browser.document
import kotlinx.browser.window
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.w3c.dom.HTMLButtonElement
import org.w3c.dom.HTMLDivElement
import org.w3c.dom.HTMLInputElement
import org.w3c.dom.HTMLSelectElement
import org.w3c.dom.events.Event

class CreateUserPage(
    override val pageType: PageType,
    override val pageManager: AttendooPageManager,
    private val httpClient: KtorHttpClient
) : Page
{
    override val pageBuilder = CreateUserPageBuilderImp()

    override fun init()
    {
        val submitButton = getButtonByID(CREATE_USER_SUBMIT)

        if (submitButton == null)
        {
            console.log("Could not find submit button")
            return
        }

        submitButton.addEventListener("click", { event: Event ->
            event.preventDefault()

            val firstNameInput = getInputByID(CREATE_USER_FIRST_NAME)
            val lastNameInput = getInputByID(CREATE_USER_LAST_NAME)
            val emailInput = getInputByID(CREATE_USER_EMAIL)
            val phoneInput = getInputByID(CREATE_USER_PHONE)

            val departmentSelect = getSelectById(CREATE_USER_DEPARTMENT)
            val newDepartmentInput = getInputByID(CREATE_USER_NEW_DEPARTMENT)
            val roleSelect = getSelectById(CREATE_USER_ROLE)
            val newRoleInput = getInputByID(CREATE_USER_NEW_ROLE)

            val usernameInput = getInputByID(CREATE_USER_USERNAME)
            val passwordInput = getInputByID(CREATE_USER_PASSWORD)

            if (firstNameInput == null || lastNameInput == null || emailInput == null || phoneInput == null ||
                departmentSelect == null || newDepartmentInput == null || roleSelect == null || newRoleInput == null ||
                usernameInput == null || passwordInput == null
            )
            {
                window.alert("Could not find all form fields")
                return@addEventListener
            }

            val firstName = firstNameInput.value.trim()
            val lastName = lastNameInput.value.trim()
            val email = emailInput.value.trim()
            val phone = phoneInput.value.trim()
            val departmentFromSelect = departmentSelect.value
            val newDepartment = newDepartmentInput.value.trim()
            val roleFromSelect = roleSelect.value
            val newRole = newRoleInput.value.trim()
            val username = usernameInput.value.trim()
            val password = passwordInput.value.trim()

            val department = newDepartment.ifEmpty { departmentFromSelect }
            val role = newRole.ifEmpty { roleFromSelect }

            if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || phone.isEmpty() ||
                department.isEmpty() || role.isEmpty() || username.isEmpty() || password.isEmpty()
            )
            {
                window.alert("Please fill in all required fields")
                return@addEventListener
            }

            val userDTO = UserDTO(
                firstName = firstName,
                lastName = lastName,
                attendooUsername = username,
                attendooPassword = password,
                email = email,
                phoneNumber = phone,
                role = role,
                userStatus = "active",
                userDepartment = department
            )

            launchDefaultCoroutine {
                try
                {
                    val response = httpClient.postJSONVia(
                        FULL_REGISTER_ENDPOINT,
                        Json.encodeToString(UserDTO.serializer(), userDTO)
                    )

                    if (response.status.value in 200..299)
                    {
                        window.alert("User created successfully")
                        firstNameInput.value = ""
                        lastNameInput.value = ""
                        emailInput.value = ""
                        phoneInput.value = ""
                        usernameInput.value = ""
                        passwordInput.value = ""
                        newDepartmentInput.value = ""
                        newRoleInput.value = ""
                    }
                    else
                    {
                        window.alert("Failed to create user: ${response.status.description}")
                    }
                }
                catch (e: Exception)
                {
                    window.alert("Error creating user: ${e.message}")
                    console.log("Error creating user: ${e.message}")
                }
            }
        })
    }

    override fun show()
    {
        val appenderBox = getDivByID(MAIN)!!

        launchDefaultCoroutine {
            val groupOptions = fetchGroupOptions()
            val roleOptions = fetchRoleOptions()

            pageBuilder.buildDynamicContent(
                appender = appenderBox,
                groupOptions = groupOptions,
                roleOptions = roleOptions
            )

            init()
        }
    }

    override fun hide()
    {
        pageBuilder.eraseDynamicElement()
    }

    private suspend fun fetchGroupOptions(): Array<String>
    {
        try
        {
            val response = httpClient.getVia(FULL_ALL_DEPARTMENTS_ENDPOINT)
            val responseText = response.bodyAsText()
            val departments: List<DepartmentDTO> = Json.decodeFromString(responseText)

            return departments.map { it.name }.toTypedArray()
        }
        catch (e: Exception)
        {
            console.error("Error fetching departments: ${e.message}")
            return arrayOf()
        }
    }

    private suspend fun fetchRoleOptions(): Array<String>
    {
        try
        {
            val response = httpClient.getVia(FULL_ALL_ROLES_ENDPOINT)
            val responseText = response.bodyAsText()
            val roles: List<RoleDTO> = Json.decodeFromString(responseText)

            return roles.map { it.name ?: "" }.filter { it.isNotEmpty() }.toTypedArray()
        }
        catch (e: Exception)
        {
            console.error("Error fetching roles: ${e.message}")
            return arrayOf()
        }
    }
}
