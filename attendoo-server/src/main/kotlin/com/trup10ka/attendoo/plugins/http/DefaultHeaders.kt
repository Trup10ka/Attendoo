package com.trup10ka.attendoo.plugins.http

import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.defaultheaders.DefaultHeaders

fun Application.configureDefaultHeaders()
{
    install(DefaultHeaders)
    {
        header("X-Engine", "Ktor")
    }
}
