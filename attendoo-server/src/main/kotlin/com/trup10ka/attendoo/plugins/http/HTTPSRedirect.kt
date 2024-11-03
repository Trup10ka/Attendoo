package com.trup10ka.attendoo.plugins.http

import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.httpsredirect.HttpsRedirect

fun Application.configureHTTPSRedirect()
{
    install(HttpsRedirect)
    {
        sslPort = 443
        permanentRedirect = true
    }
}
