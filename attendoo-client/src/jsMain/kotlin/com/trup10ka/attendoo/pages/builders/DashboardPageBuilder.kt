package com.trup10ka.attendoo.pages.builders

import org.w3c.dom.HTMLElement

interface DashboardPageBuilder : PageBuilder
{
    fun buildCalendarBox()
    
    fun buildListOfUsers()
    
    override fun buildDynamicElement(appender: HTMLElement?)
    {
        throw UnsupportedOperationException("This is not supported for DashboardPageBuilder, if called, please contact the developer")
    }
}
