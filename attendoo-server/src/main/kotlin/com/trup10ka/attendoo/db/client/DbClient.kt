package com.trup10ka.attendoo.db.client

abstract class DbClient
{
    lateinit var userService: UserService

    abstract fun connect()

    abstract fun init()
}
