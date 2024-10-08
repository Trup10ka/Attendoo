package com.trup10ka.attendoo

import com.trup10ka.attendoo.plugins.configureDatabases
import com.trup10ka.attendoo.plugins.configureHTTP
import com.trup10ka.attendoo.plugins.configureRouting
import com.trup10ka.attendoo.plugins.configureSecurity
import com.trup10ka.attendoo.plugins.configureSerialization
import com.trup10ka.attendoo.plugins.configureSockets
import io.ktor.server.application.Application

fun Application.module()
{
    configureSecurity()
    configureHTTP()
    configureSerialization()
    configureDatabases()
    configureSockets()
    configureRouting()
}