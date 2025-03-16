package com.trup10ka.attendoo.api.attendances

import com.trup10ka.attendoo.db.services.UserAttendanceService
import com.trup10ka.attendoo.db.services.UserService
import io.ktor.server.auth.jwt.JWTPrincipal
import io.ktor.server.auth.principal
import io.ktor.server.routing.Route
import io.ktor.server.routing.get

fun Route.routeGetUserAttendances(userAttendanceService: UserAttendanceService, userService: UserService)
{
    get("/attendances")
    {
        val principal = call.principal<JWTPrincipal>()
        val userId = userAttendanceService.getAttendanceByUserId()
    }
}
