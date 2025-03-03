package com.trup10ka.attendoo.pages.builders

import com.trup10ka.attendoo.data.User
import org.w3c.dom.HTMLElement

class DashboardPageBuilderImp() : DashboardPageBuilder
{
    override val currentlyActiveHTMLElements = mutableSetOf<HTMLElement>()
    
    
    override fun buildAttendanceBox()
    {
        TODO("Not yet implemented")
    }
    
    override fun buildListOfUsers(users: List<User>)
    {
        TODO("Not yet implemented")
    }
    
    override fun eraseDynamicElement()
    {
        TODO("Not yet implemented")
    }
}
