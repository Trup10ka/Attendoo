package com.trup10ka.attendoo.pages.builders

import com.trup10ka.attendoo.data.Request
import org.w3c.dom.HTMLElement

interface RequestPageBuilder : PageBuilder
{
    fun buildRequestContainer(appender: HTMLElement? = null, request: Request)
    
    override fun buildDynamicElement(appender: HTMLElement?)
    {
        throw UnsupportedOperationException("This should not be a reachable part of a program. Contact the developer.")
    }
}
