package com.trup10ka.attendoo.api.users

import com.trup10ka.attendoo.EDIT_USER_ENDPOINT
import com.trup10ka.attendoo.ERROR_JSON_FIELD_NAME
import com.trup10ka.attendoo.SUCCESS_JSON_FIELD_NAME
import com.trup10ka.attendoo.db.services.UserService
import com.trup10ka.attendoo.dto.UserDTO
import io.github.oshai.kotlinlogging.KotlinLogging
import io.ktor.http.HttpStatusCode
import io.ktor.server.auth.jwt.JWTPrincipal
import io.ktor.server.auth.principal
import io.ktor.server.plugins.origin
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get

val logger = KotlinLogging.logger {}

fun Route.routeEditUser(userService: UserService)
{
    //TODO: Implement only for admin
    get(EDIT_USER_ENDPOINT)
    {
        logger.info { "Received request for EDIT USER from ${call.request.origin.remoteHost}:${call.request.origin.remotePort}" }
        
        val principal = call.principal<JWTPrincipal>()
        val user = call.receive<UserDTO>()
        
        val updatedUser = userService.updateUserByUsername(user)
        
        if (updatedUser)
        {
            call.respond(HttpStatusCode.OK, SUCCESS_JSON_FIELD_NAME to "User updated successfully")
        }
        else
        {
            call.respond(HttpStatusCode.BadRequest, ERROR_JSON_FIELD_NAME to "User could not be updated")
        }
    }
}
