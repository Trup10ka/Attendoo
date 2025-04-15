package com.trup10ka.attendoo.api.users

import com.trup10ka.attendoo.GET_ALL_USERS_ENDPOINT
import com.trup10ka.attendoo.db.dbQuery
import com.trup10ka.attendoo.db.services.UserService
import com.trup10ka.attendoo.db.toDTO
import com.trup10ka.attendoo.dto.UserDTO
import io.ktor.http.HttpStatusCode
import io.ktor.server.auth.jwt.JWTPrincipal
import io.ktor.server.auth.principal
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get

fun Route.routeGetAllUsersFromDepartment(userService: UserService)
{
    get(GET_ALL_USERS_ENDPOINT)
    {
        // TODO: Add returning only if authorized as ADMIN
        val principal = call.principal<JWTPrincipal>()
        val department = call.receive<String>()
        
        val users = dbQuery {
            userService.getAllUsersFromDepartment(department).map {  it.toDTO<UserDTO>() }
        }
        
        call.respond(HttpStatusCode.OK, users)
    }
}
