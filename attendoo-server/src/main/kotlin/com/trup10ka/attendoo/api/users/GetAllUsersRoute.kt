package com.trup10ka.attendoo.api.users

import com.trup10ka.attendoo.db.services.UserService
import io.ktor.http.HttpStatusCode
import io.ktor.server.auth.jwt.JWTPrincipal
import io.ktor.server.auth.principal
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get

fun Route.routeGetAllUsersFromDepartment(userService: UserService)
{
    get("/all-users")
    {
        // TODO: Add returning only if authorized as ADMIN
        val principal = call.principal<JWTPrincipal>()
        val department = call.receive<String>()
        
        val users = userService.getAllUsersFromDepartment(department)
        
        call.respond(HttpStatusCode.OK, users)
    }
}
