package com.trup10ka.attendoo.plugins

import io.ktor.server.application.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import io.ktor.websocket.*
import kotlin.time.Duration

fun Application.configureSockets()
{
    install(WebSockets)
    {
        pingPeriod = Duration.parseOrNull("30s")
        timeout = Duration.parse("15s")
        maxFrameSize = Long.MAX_VALUE
        masking = false
    }
}
