package com.trup10ka.attendoo.pages.builders

import com.trup10ka.attendoo.data.User
import org.w3c.dom.HTMLElement

class UsersPageBuilderImp() : UsersPageBuilder
{
    override val currentlyActiveHTMLElements = mutableSetOf<HTMLElement>()
    
    override fun buildUserContainer(user: User)
    {
        TODO("Not yet implemented")
    }
    
    override fun eraseDynamicElement()
    {
        TODO("Not yet implemented")
    }
}
