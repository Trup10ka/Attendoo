package com.trup10ka.attendoo.pages.builders

import com.trup10ka.attendoo.data.User

interface UsersPageBuilder : PageBuilder
{
    fun buildUserContainer(user: User)
}
