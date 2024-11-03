package com.trup10ka.attendoo.plugins.http

import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.compression.Compression
import io.ktor.server.plugins.compression.deflate
import io.ktor.server.plugins.compression.gzip


fun Application.configureCompression()
{
    install(Compression)
    {
        gzip {
            priority = 1.0
        }
        deflate {
            priority = 0.9
        }
    }
}
