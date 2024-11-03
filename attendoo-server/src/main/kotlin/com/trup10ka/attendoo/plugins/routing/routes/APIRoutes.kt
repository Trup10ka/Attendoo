package com.trup10ka.attendoo.plugins.routing.routes

import io.ktor.server.response.respondText
import io.ktor.server.routing.Route
import io.ktor.server.routing.get

fun Route.routeAPICalls()
{
    get("/api/v1/attendees") {
        call.respondText("Version 1 of the Attendees API")
    }
}
