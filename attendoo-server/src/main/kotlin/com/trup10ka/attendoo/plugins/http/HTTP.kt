package com.trup10ka.attendoo.plugins.http

import com.trup10ka.attendoo.config.ConfigDistributor.config
import io.ktor.server.application.*
import io.ktor.server.plugins.compression.*
import io.ktor.server.plugins.defaultheaders.*

fun Application.configureHTTP()
{
    configureCompression()
    configureCORSPolicy()
    configureDefaultHeaders()

    if (config.isSecureConnection)
        configureHTTPSRedirect()
}
