package com.trup10ka.attendoo.db.client

import com.trup10ka.attendoo.db.services.EmployeeService

abstract class DbClient
{
    lateinit var userService: EmployeeService

    abstract fun connect()

    abstract fun init()
}
