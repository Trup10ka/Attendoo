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
            id = "${request.user.attendooUsername}-request",
            clazz = stylesOf(INNER_CONTAINER, REQUEST_CONTAINER),
            child = createDiv(
                id = request.user.attendooUsername,
                clazz = stylesOf(CONTAINER_TAB, REQUEST),
                children = arrayOf(
                    createContainerHeader(request.user),
                    createInfoContainer(request),
                    createActionsContainer(request)
                )
            )
        )
        
        appender?.appendChild(requestsContainer)
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
    
    private fun createContainerHeader(user: User): HTMLDivElement
    {
        return createDiv(
            id = "${user.attendooUsername}-header",
            clazz = stylesOf(MINI_CONTAINER_HEADER),
            children = arrayOf(
                createDiv(
                    id = "${user.attendooUsername}-name",
                    clazz = stylesOf(ONE_LINE_CONTAINER, EMPLOYEE_NAME_CONTAINER),
                    text = "${user.firstName} ${user.lastName}"
                ),
                createDiv(
                    clazz = stylesOf(ONE_LINE_CONTAINER, AVATAR),
                    // add avatar somehow
                )
            )
        )
    }
    
    private fun createInfoContainer(request: Request): HTMLDivElement
    {
        return createDiv(
            id = "${request.user.attendooUsername}-info",
            clazz = arrayOf("request-info"),
            children = arrayOf(
                createDiv(
                    id = "${request.user.attendooUsername}-company",
                    clazz = stylesOf(ONE_LINE_CONTAINER),
                    text = request.company
                ),
                createDiv(
                    id = "${request.user.attendooUsername}-note",
                    clazz = stylesOf(ONE_LINE_CONTAINER),
                    text = request.note
                )
            )
        )
    }
    
    private fun createActionsContainer(request: Request): HTMLDivElement
    {
        return createDiv(
            id = "${request.user.attendooUsername}-actions",
            clazz = stylesOf(ACTIONS_CONTAINER),
            children = arrayOf(
                createSpan(clazz = stylesOf(MATERIAL_SYMBOLS_OUTLINED, ICON), text = "open_in_full"),
                createSpan(clazz = stylesOf(MATERIAL_SYMBOLS_OUTLINED, ICON), text = "edit"),
                createSpan(clazz = stylesOf(MATERIAL_SYMBOLS_OUTLINED, ICON), text = "delete")
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
