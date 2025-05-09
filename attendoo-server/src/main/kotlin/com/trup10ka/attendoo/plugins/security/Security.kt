package com.trup10ka.attendoo.plugins.security

import com.trup10ka.attendoo.config.ConfigDistributor.config
import io.ktor.server.application.Application

fun Application.configureSecurity()
{
    if (config.isSecureConnection)
    {
        configureSessionCookie()
    }
    configureJWT()
}
