package com.trup10ka.attendoo.pages.builders

import org.w3c.dom.HTMLElement

interface CreateUserPageBuilder : PageBuilder
{
    fun buildDynamicContent(appender: HTMLElement? = null, groupOptions: Array<String>, roleOptions: Array<String>)
    
    override fun buildDynamicElement(appender: HTMLElement?)
    {
        throw UnsupportedOperationException("This method is not supported in this context, contact the developer for more information")
    }
    
    override fun eraseDynamicElement()
}
