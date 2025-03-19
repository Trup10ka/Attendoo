package com.trup10ka.attendoo.api.attendances

import com.trup10ka.attendoo.api.attendooUsername
import com.trup10ka.attendoo.db.services.UserAttendanceService
import com.trup10ka.attendoo.db.services.UserService
import io.ktor.http.HttpStatusCode
import io.ktor.server.auth.jwt.JWTPrincipal
import io.ktor.server.auth.principal
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get

fun Route.routeGetUserAttendances(userAttendanceService: UserAttendanceService, userService: UserService)
{
    get("/user")
    {
        val principal = call.principal<JWTPrincipal>()
        
        if (principal == null)
        {
            call.respond(HttpStatusCode.Unauthorized, mapOf("error" to "No token provided"))
            return@get
        }
        
        val user = userService.getUserByUsername(principal.attendooUsername)
        if (user == null)
        {
            call.respond(HttpStatusCode.NotFound, mapOf("error" to "User not found"))
            return@get
        }
        
        val allUserAttendances = userAttendanceService.getAttendanceByUserId(user.id.value)
        
        call.respond(HttpStatusCode.OK, allUserAttendances)
    }
}
