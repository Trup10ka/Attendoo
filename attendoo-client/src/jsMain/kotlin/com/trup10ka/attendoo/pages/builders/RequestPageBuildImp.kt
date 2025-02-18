package com.trup10ka.attendoo.pages.builders

import com.trup10ka.attendoo.data.Request
import com.trup10ka.attendoo.data.User
import com.trup10ka.attendoo.util.arrayOf
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
            clazz = arrayOf(INNER_CONTAINER, REQUEST_CONTAINER),
            child = createDiv(
                clazz = arrayOf(CONTAINER_TAB, REQUEST),
                children = arrayOf(
                    createContainerHeader(request.user),
                    createInfoContainer(request),
                    createActionsContainer()
                )
            )
        )
        
        appender?.appendChild(requestsContainer)
    }
    
    private fun createContainerHeader(user: User): HTMLDivElement
    {
        return createDiv(
            clazz = arrayOf(MINI_CONTAINER_HEADER),
            children = arrayOf(
                createDiv(
                    clazz = arrayOf(ONE_LINE_CONTAINER, EMPLOYEE_NAME_CONTAINER),
                    text = user.fullName
                ),
                createDiv(
                    clazz = arrayOf(ONE_LINE_CONTAINER, AVATAR),
                    // add avatar somehow
                )
            )
        )
    }
    
    private fun createInfoContainer(request: Request): HTMLDivElement
    {
        return createDiv(
            clazz = arrayOf("request-info"),
            children = arrayOf(
                createDiv(clazz = arrayOf(ONE_LINE_CONTAINER), text = request.company),
                createDiv(clazz = arrayOf(ONE_LINE_CONTAINER), text = request.note)
            )
        )
    }
    
    private fun createActionsContainer(): HTMLDivElement
    {
        return createDiv(
            clazz = arrayOf(ACTIONS_CONTAINER),
            children = arrayOf(
                createSpan(clazz = arrayOf(MATERIAL_SYMBOLS_OUTLINED, ICON), text = "open_in_full"),
                createSpan(clazz = arrayOf(MATERIAL_SYMBOLS_OUTLINED, ICON), text = "edit"),
                createSpan(clazz = arrayOf(MATERIAL_SYMBOLS_OUTLINED, ICON), text = "delete")
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
