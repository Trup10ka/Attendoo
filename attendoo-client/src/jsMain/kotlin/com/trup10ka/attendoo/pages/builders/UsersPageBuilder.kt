package com.trup10ka.attendoo.pages.builders

import com.trup10ka.attendoo.data.User
import org.w3c.dom.HTMLElement

interface UsersPageBuilder : PageBuilder
{
    fun buildUserContainer(user: User)
    
    override fun buildDynamicElement(appender: HTMLElement?)
    {
        throw UnsupportedOperationException("This method is not supported in this context, contact the developer for more information")
    }
}
