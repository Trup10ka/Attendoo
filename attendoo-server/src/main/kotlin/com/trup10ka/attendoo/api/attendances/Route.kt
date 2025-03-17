package com.trup10ka.attendoo.api.attendances

import com.trup10ka.attendoo.db.client.DbClient
import com.trup10ka.attendoo.db.services.UserAttendanceService
import com.trup10ka.attendoo.db.services.UserService
import io.ktor.server.routing.Route
import io.ktor.server.routing.route

fun Route.routeAttendances(userAttendanceService: UserAttendanceService, userService: UserService)
{
    route("/attendances") {
        routeGetAllAttendances(userAttendanceService, userService)
        routeGetUserAttendances(userAttendanceService, userService)
    }
}
