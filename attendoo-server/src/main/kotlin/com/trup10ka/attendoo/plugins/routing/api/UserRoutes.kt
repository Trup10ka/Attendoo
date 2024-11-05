package com.trup10ka.attendoo.plugins.routing.api

import com.trup10ka.attendoo.User
import io.ktor.server.response.respondText
import io.ktor.server.routing.Route
import io.ktor.server.routing.get

fun Route.getAllUsers()
{
    get("/api/v1/users") {
        call.respondText(User().name)
    }
}
