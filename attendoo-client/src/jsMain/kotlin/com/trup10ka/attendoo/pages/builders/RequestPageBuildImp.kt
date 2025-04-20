package com.trup10ka.attendoo.pages.builders

import com.trup10ka.attendoo.data.Request
import com.trup10ka.attendoo.data.User
import com.trup10ka.attendoo.util.stylesOf
import com.trup10ka.attendoo.util.createDiv
import org.w3c.dom.HTMLElement
import com.trup10ka.attendoo.pages.constant.StyleClass.*
import com.trup10ka.attendoo.util.createSpan
import org.w3c.dom.HTMLDivElement

class RequestPageBuildImp : RequestPageBuilder
{
    override val currentlyActiveHTMLElements = mutableSetOf<HTMLElement>()

    override fun buildRequestContainer(appender: HTMLElement?, request: Request)
    {
        val requestsContainer = createDiv(
            id = "${request.proposer.attendooUsername}-request",
            clazz = stylesOf(INNER_CONTAINER, REQUEST_CONTAINER),
            child = createDiv(
                id = request.proposer.attendooUsername,
                clazz = stylesOf(CONTAINER_TAB, REQUEST),
                children = arrayOf(
                    createContainerHeader(request.proposer, request.proposed),
                    createInfoContainer(request),
                    createActionsContainer(request)
                )
            )
        )

        appender?.appendChild(requestsContainer)
        currentlyActiveHTMLElements.add(requestsContainer)
    }

    override fun buildNoRequestsFoundMessage(appender: HTMLElement?)
    {
        val messageContainer = createDiv(
            id = "no-requests-message",
            clazz = stylesOf(INNER_CONTAINER, CENTER),
            text = "No requests found"
        )

        currentlyActiveHTMLElements.add(messageContainer)
        appender?.appendChild(messageContainer)
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
            clazz = arrayOf("request-info"),
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
        return createDiv(
            id = "${request.proposer.attendooUsername}-actions",
            clazz = stylesOf(ACTIONS_CONTAINER),
            children = arrayOf(
                createSpan(clazz = stylesOf(MATERIAL_SYMBOLS_OUTLINED, ICON), text = "check_circle"),
                createSpan(clazz = stylesOf(MATERIAL_SYMBOLS_OUTLINED, ICON), text = "cancel")
            )
        )
    }

    override fun eraseDynamicElement()
    {
        currentlyActiveHTMLElements.forEach {
            it.remove()
        }
        currentlyActiveHTMLElements.clear()
    }
}
