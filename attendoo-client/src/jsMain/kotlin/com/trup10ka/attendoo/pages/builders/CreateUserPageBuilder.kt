package com.trup10ka.attendoo.pages.builders

import org.w3c.dom.HTMLElement

interface CreateUserPageBuilder
{
    val currentlyActiveHTMLElements: MutableSet<HTMLElement>
    
    fun buildDynamicContent(appender: HTMLElement? = null, groupOptions: Array<String>, roleOptions: Array<String>)
    
    fun eraseDynamicElement()
}
