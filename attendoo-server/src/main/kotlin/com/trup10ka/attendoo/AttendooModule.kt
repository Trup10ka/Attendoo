package com.trup10ka.attendoo

import com.trup10ka.attendoo.plugins.configureSecurity
import com.trup10ka.attendoo.plugins.http.configureHTTP
import com.trup10ka.attendoo.plugins.routing.configureRouting
import com.trup10ka.attendoo.plugins.configureSerialization
import com.trup10ka.attendoo.plugins.configureSockets
import com.trup10ka.attendoo.plugins.configureStatusPage
import io.ktor.server.application.Application

fun Application.attendooModule()
{
    configureSecurity()
    configureHTTP()
    configureSerialization()
    configureSockets()
    configureStatusPage()
    configureRouting()
}
