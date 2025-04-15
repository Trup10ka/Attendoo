package com.trup10ka.attendoo.api.attendances

import com.trup10ka.attendoo.ALL_ATTENDANCE_ENDPOINT
import com.trup10ka.attendoo.ATTENDANCES_ENDPOINT
import com.trup10ka.attendoo.db.client.DbClient
import com.trup10ka.attendoo.db.services.UserAttendanceService
import com.trup10ka.attendoo.db.services.UserService
import io.ktor.server.auth.jwt.JWTPrincipal
import io.ktor.server.auth.principal
import io.ktor.server.routing.Route
import io.ktor.server.routing.get

fun Route.routeGetAllAttendances(userAttendanceService: UserAttendanceService, userService: UserService)
{
    get(ALL_ATTENDANCE_ENDPOINT)
    {
        userAttendanceService.getAllAttendance()
    }
}
