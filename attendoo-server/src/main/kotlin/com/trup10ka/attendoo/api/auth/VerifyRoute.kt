package com.trup10ka.attendoo.api.auth

import io.ktor.http.HttpStatusCode
import io.ktor.server.plugins.origin
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get

fun Route.routeVerify()
{
    get("/verify")
    {
        logger.info { "Received request for VERIFY from ${call.request.origin.remoteHost}:${call.request.origin.remotePort}" }

        call.respond(HttpStatusCode.OK, mapOf("success" to true))
    }
}
