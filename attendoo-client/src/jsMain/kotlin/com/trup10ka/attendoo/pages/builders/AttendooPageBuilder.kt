package com.trup10ka.attendoo.pages.builders

import org.w3c.dom.HTMLElement

interface AttendooPageBuilder
{
    val currentlyActiveHTMLElements: MutableSet<HTMLElement>
    
    fun buildDynamicElement(appender: HTMLElement? = null)
    fun eraseDynamicElement()
}
