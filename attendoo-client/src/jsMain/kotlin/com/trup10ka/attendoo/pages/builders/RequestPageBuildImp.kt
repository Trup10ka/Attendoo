package com.trup10ka.attendoo.pages.builders

import com.trup10ka.attendoo.FULL_USER_ATTENDANCE_APPROVE_ENDPOINT
import com.trup10ka.attendoo.FULL_USER_ATTENDANCE_REJECT_ENDPOINT
import com.trup10ka.attendoo.data.Request
import com.trup10ka.attendoo.data.RequestApproval
import com.trup10ka.attendoo.data.SelectOption
import com.trup10ka.attendoo.data.User
import com.trup10ka.attendoo.fetch.HttpClient
import com.trup10ka.attendoo.util.stylesOf
import com.trup10ka.attendoo.util.createButton
import com.trup10ka.attendoo.util.createDiv
import com.trup10ka.attendoo.util.createForm
import com.trup10ka.attendoo.util.createHeader
import com.trup10ka.attendoo.util.createSelectWithOptions
import com.trup10ka.attendoo.util.createSpan
import org.w3c.dom.HTMLElement
import com.trup10ka.attendoo.pages.constant.StyleClass.*
import com.trup10ka.attendoo.pages.constant.ElementID.*
import com.trup10ka.attendoo.util.launchDefaultCoroutine
import io.ktor.client.statement.HttpResponse
import kotlinx.browser.window
import kotlinx.serialization.json.Json
import org.w3c.dom.HTMLDivElement
import org.w3c.dom.HTMLSpanElement
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class RequestPageBuildImp(private val httpClient: HttpClient) : RequestPageBuilder
{
    override val currentlyActiveHTMLElements = mutableSetOf<HTMLElement>()
    
    override fun buildDynamicElement(appender: HTMLElement?)
    {
        val requestsContainer = createDiv(
            id = REQUESTS_CONTAINER_ID,
            clazz = stylesOf( INNER_CONTAINER, REQUESTS_CONTAINER),
            children = arrayOf(
                createDiv(
                    id = REQUESTS_CONTAINER_LEFT,
                    clazz = stylesOf(SECTION, LEFT_R)
                ),
                createDiv(
                    id = REQUESTS_CONTAINER_RIGHT,
                    clazz = stylesOf( SECTION, RIGHT_R)
                )
            )
        )

        appender?.appendChild(requestsContainer)
        currentlyActiveHTMLElements.add(requestsContainer)
    }

    @OptIn(ExperimentalUuidApi::class)
    override fun buildRequestContainer(appender: HTMLElement?, request: Request)
    {
        val requestsContainer = createDiv(
            id = "${request.proposer.attendooUsername}-${Uuid.random()}",
            clazz = stylesOf(CONTAINER_TAB),
            children = arrayOf(
                createContainerHeader(request.proposer, request.proposed),
                createInfoContainer(request),
                createActionsContainer(request)
            )
        )

        appender?.appendChild(requestsContainer)
        currentlyActiveHTMLElements.add(requestsContainer)
    }

    override fun buildNoRequestsFoundMessage(appender: HTMLElement?)
    {
        val messageContainer = createDiv(
            id = "no-requests-message",
            clazz = stylesOf( CENTER, CONTAINER_FIELD),
            text = "No requests found"
        )

        currentlyActiveHTMLElements.add(messageContainer)
        appender?.appendChild(messageContainer)
    }
    
    override fun buildRequestCreationForm(appender: HTMLElement?, usernames: List<String>, statuses: List<String>)
    {
        val formContainer = createDiv(
            id = "request-form-container",
            clazz = stylesOf( CENTER, CONTAINER_FIELD, FULL_WIDTH),
            child = createForm(
                id = CREATE_REQUEST_FORM,
                clazz = stylesOf(CONTAINER_TAB, REQUESTS_FORM),
                children = arrayOf(
                    createHeader(text = "Create Request"),
                    createSelectWithOptions(
                        id = REQUEST_FORM_USERNAMES,
                        clazz = stylesOf(ONE_LINE_CONTAINER),
                        options = usernames.map { SelectOption(it, it) }.toTypedArray()
                    ),
                    createSelectWithOptions(
                        id = REQUEST_FORM_STATUS,
                        clazz = stylesOf(ONE_LINE_CONTAINER),
                        options = statuses.map { SelectOption(it, it) }.toTypedArray()
                    ),
                    createButton(
                        id = REQUEST_FORM_SUBMIT,
                        clazz = stylesOf(SUBMIT_BUTTON),
                        text = "Submit"
                    )
                )
            )
        )

        appender?.appendChild(formContainer)
        currentlyActiveHTMLElements.add(formContainer)
    }
    
    private fun createContainerHeader(proposer: User, proposed: User): HTMLDivElement
    {
        return createDiv(
            id = "${proposer.attendooUsername}-header",
            clazz = stylesOf(MINI_CONTAINER_HEADER),
            children = arrayOf(
                createDiv(
                    id = "${proposer.attendooUsername}-from",
                    clazz = stylesOf(ONE_LINE_CONTAINER, EMPLOYEE_NAME_CONTAINER),
                    text = "From: ${proposer.firstName} ${proposer.lastName}"
                ),
                createDiv(
                    id = "${proposer.attendooUsername}-to",
                    clazz = stylesOf(ONE_LINE_CONTAINER, EMPLOYEE_NAME_CONTAINER),
                    text = "To: ${proposed.firstName} ${proposed.lastName}"
                )
            )
        )
    }

    private fun createInfoContainer(request: Request): HTMLDivElement
    {
        return createDiv(
            id = "${request.proposer.attendooUsername}-info",
            clazz = stylesOf(REQUEST_INFO),
            children = arrayOf(
                createDiv(
                    id = "${request.proposer.attendooUsername}-department",
                    clazz = stylesOf(ONE_LINE_CONTAINER),
                    text = "Proposing for department: ${request.proposedDepartment}"
                ),
                createDiv(
                    id = "${request.proposer.attendooUsername}-current-status",
                    clazz = stylesOf(ONE_LINE_CONTAINER),
                    text = "Current status: ${request.currentStatus}"
                ),
                createDiv(
                    id = "${request.proposer.attendooUsername}-proposed-status",
                    clazz = stylesOf(ONE_LINE_CONTAINER),
                    text = "Proposed status: ${request.proposedStatus}"
                )
            )
        )
    }

    private fun createActionsContainer(request: Request): HTMLDivElement
    {
        val checkCircleAction = createSpan(clazz = stylesOf(MATERIAL_SYMBOLS_OUTLINED, ICON), text = "check_circle")
        val cancelAction = createSpan(clazz = stylesOf(MATERIAL_SYMBOLS_OUTLINED, ICON), text = "cancel")
        
        setupListenersForActions(request,checkCircleAction, cancelAction)
        
        return createDiv(
            id = "${request.proposer.attendooUsername}-actions-${request.currentStatus}-${request.proposedStatus}",
            clazz = stylesOf(ACTIONS_CONTAINER),
            children = arrayOf(
                checkCircleAction,
                cancelAction
            )
        )
    }
    
    private fun setupListenersForActions(currentlyCreatedRequest: Request, approveAction: HTMLSpanElement, cancelAction: HTMLSpanElement)
    {
        val requestApproval = RequestApproval(
            userProposing = currentlyCreatedRequest.proposer.attendooUsername,
            currentStatus = currentlyCreatedRequest.currentStatus,
            proposedStatus = currentlyCreatedRequest.proposedStatus
        )
        approveAction.addEventListener("click", {
            launchDefaultCoroutine {
                val response = httpClient.postJSONVia(FULL_USER_ATTENDANCE_APPROVE_ENDPOINT, Json.encodeToString(requestApproval)) as HttpResponse
                
                if (response.status.value == 200)
                {
                    window.alert("Successfully approved request")
                    window.location.reload()
                }
                else
                {
                    window.alert("Failed to approve request")
                }
            }
        })
        
        cancelAction.addEventListener("click", {
            launchDefaultCoroutine {
                val response = httpClient.postJSONVia(FULL_USER_ATTENDANCE_REJECT_ENDPOINT, Json.encodeToString(requestApproval)) as HttpResponse
                
                if (response.status.value == 200)
                {
                    window.alert("Successfully cancelled request")
                    window.location.reload()
                }
                else
                {
                    window.alert("Failed to cancel request")
                }
            }
        })
    }

    override fun eraseDynamicElement()
    {
        currentlyActiveHTMLElements.forEach {
            it.remove()
        }
        currentlyActiveHTMLElements.clear()
    }
}
