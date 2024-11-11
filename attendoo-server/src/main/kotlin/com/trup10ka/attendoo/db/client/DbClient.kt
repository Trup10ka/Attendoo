package com.trup10ka.attendoo.db.client

import com.trup10ka.attendoo.db.services.UserService

abstract class DbClient
{
    lateinit var userService: UserService

    abstract fun connect()

    abstract fun init()
}
