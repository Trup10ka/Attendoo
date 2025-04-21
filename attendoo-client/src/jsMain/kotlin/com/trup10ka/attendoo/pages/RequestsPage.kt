package com.trup10ka.attendoo.pages

import com.trup10ka.attendoo.AttendooPageManager
import com.trup10ka.attendoo.FULL_ALL_REQUESTS_ENDPOINT
import com.trup10ka.attendoo.FULL_ALL_USERS_ENDPOINT
import com.trup10ka.attendoo.FULL_CREATE_REQUEST_ENDPOINT
import com.trup10ka.attendoo.FULL_STATUSES_ENDPOINT
import com.trup10ka.attendoo.data.Request
import com.trup10ka.attendoo.data.User
import com.trup10ka.attendoo.dto.RequestDTONoProposer
import com.trup10ka.attendoo.fetch.HttpClient
import com.trup10ka.attendoo.pages.builders.RequestPageBuildImp
import com.trup10ka.attendoo.pages.constant.ElementID.*
import com.trup10ka.attendoo.pages.constant.PageType
import com.trup10ka.attendoo.pages.constant.StyleClass.CENTER
import com.trup10ka.attendoo.pages.constant.StyleClass.INNER_CONTAINER
import com.trup10ka.attendoo.util.getDivByID
import com.trup10ka.attendoo.util.launchDefaultCoroutine
import com.trup10ka.attendoo.util.stylesOf
import com.trup10ka.attendoo.util.createHeader
import com.trup10ka.attendoo.util.getButtonByID
import com.trup10ka.attendoo.util.getSelectById
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import kotlinx.serialization.json.Json

class RequestsPage(
    override val pageType: PageType,
    override val pageManager: AttendooPageManager,
    private val httpClient: HttpClient
) : Page
{
    override val pageBuilder = RequestPageBuildImp(httpClient)
    
    private val requests = mutableListOf<Request>()
    
    override fun init()
    {
        buildPage()
    }
    
    override fun show()
    {
        pageBuilder.buildDynamicElement(getDivByID(MAIN))
        init()
    }
    
    override fun hide()
    {
        pageBuilder.eraseDynamicElement()
    }
    
    private fun setupListenerForSubmitButton()
    {
        val submitButton = getButtonByID(REQUEST_FORM_SUBMIT)!!
        
        submitButton.addEventListener("click", {
            it.preventDefault()
            
            val userSelect = getSelectById(REQUEST_FORM_USERNAMES)!!
            val statusSelect = getSelectById(REQUEST_FORM_STATUS)!!
            
            val selectedUsername = userSelect.value
            val selectedStatus = statusSelect.value
            
            if (selectedUsername.isNotEmpty() && selectedStatus.isNotEmpty())
            {
                launchDefaultCoroutine {
                    try
                    {
                        val newRequest = RequestDTONoProposer(
                            proposed = selectedUsername,
                            proposedStatus = selectedStatus,
                        )
                        
                        val response = httpClient.postJSONVia(
                            FULL_CREATE_REQUEST_ENDPOINT,
                            Json.encodeToString(RequestDTONoProposer.serializer(), newRequest)
                        ) as HttpResponse
                        
                        if (response.status.value in 200..299)
                        {
                            console.log("Request submitted successfully")
                            userSelect.value = ""
                            statusSelect.value = ""
                            
                            buildPage()
                        }
                        else
                        {
                            console.error("Failed to submit request: ${response.status}")
                        }
                    }
                    catch (e: Exception)
                    {
                        console.error("Error submitting request: ${e.message}")
                    }
                }
            }
            else
            {
                console.error("Please select both a user and a status")
            }
        }
        )
    }
    
    private fun buildPage()
    {
        launchDefaultCoroutine {
            try
            {
                val fetchedRequests = httpClient.getVia(FULL_ALL_REQUESTS_ENDPOINT) as HttpResponse
                val fetchedAllUsersFromDepartment = httpClient.getVia(FULL_ALL_USERS_ENDPOINT) as HttpResponse
                val fetchedAllStatuses = httpClient.getVia(FULL_STATUSES_ENDPOINT) as HttpResponse
                
                val responseText = fetchedRequests.bodyAsText()
                val allUsersFromDepartmentText = fetchedAllUsersFromDepartment.bodyAsText()
                val allStatusesText = fetchedAllStatuses.bodyAsText()
                
                val parsedRequests: List<Request> = Json.decodeFromString(responseText)
                val parsedAllUsersFromDepartment: List<User> = Json.decodeFromString(allUsersFromDepartmentText)
                val parsedAllStatuses: List<String> = Json.decodeFromString(allStatusesText)
                
                requests.clear()
                requests.addAll(parsedRequests)
                
                val requestContainer = getDivByID(REQUESTS_CONTAINER_LEFT)
                
                if (requests.isEmpty())
                {
                    val messageContainer = createHeader(
                        clazz = stylesOf(INNER_CONTAINER, CENTER),
                        text = "No requests found"
                    )
                    
                    requestContainer?.appendChild(messageContainer)
                }
                else
                {
                    requests.forEach { request ->
                        pageBuilder.buildRequestContainer(requestContainer, request)
                    }
                }
                
                val requestContainerRight = getDivByID(REQUESTS_CONTAINER_RIGHT)
                pageBuilder.buildRequestCreationForm(
                    requestContainerRight,
                    parsedAllUsersFromDepartment.map { it.attendooUsername },
                    parsedAllStatuses
                )
            }
            catch (e: Exception)
            {
                console.error("Error fetching requests: ${e.message}")
            }
            setupListenerForSubmitButton()
        }
    }
}
