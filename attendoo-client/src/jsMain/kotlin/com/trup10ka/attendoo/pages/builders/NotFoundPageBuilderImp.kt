package com.trup10ka.attendoo.pages.builders

import org.w3c.dom.HTMLElement

class NotFoundPageBuilderImp() : NotFoundPageBuilder
{
    override val currentlyActiveHTMLElements: MutableSet<HTMLElement> = mutableSetOf()
    
    override fun buildDynamicElement(appender: HTMLElement?)
    {
        TODO("Not yet implemented")
    }
    
    override fun eraseDynamicElement()
    {
        TODO("Not yet implemented")
    }
}
