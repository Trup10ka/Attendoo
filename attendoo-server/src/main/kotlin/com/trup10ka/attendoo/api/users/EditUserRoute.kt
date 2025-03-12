package com.trup10ka.attendoo.api.users

import com.trup10ka.attendoo.db.client.DbClient
import com.trup10ka.attendoo.dto.UserDTO
import io.github.oshai.kotlinlogging.KotlinLogging
import io.ktor.server.auth.authenticate
import io.ktor.server.auth.jwt.JWTPrincipal
import io.ktor.server.auth.principal
import io.ktor.server.plugins.origin
import io.ktor.server.request.receive
import io.ktor.server.routing.Route
import io.ktor.server.response.respond
import io.ktor.server.routing.get

val logger = KotlinLogging.logger {}

fun Route.routeEditUser(dbClient: DbClient)
{
    get("/edit-user")
    {
        logger.info { "Received request for EDIT USER from ${call.request.origin.remoteHost}:${call.request.origin.remotePort}" }
        val principal = call.principal<JWTPrincipal>()
        println(principal)
    }
}
