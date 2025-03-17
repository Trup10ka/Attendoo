package com.trup10ka.attendoo.api.attendances

import com.trup10ka.attendoo.db.client.DbClient
import com.trup10ka.attendoo.db.services.UserAttendanceService
import com.trup10ka.attendoo.db.services.UserService
import io.ktor.server.routing.Route

fun Route.routeGetAllAttendances(userAttendanceService: UserAttendanceService, userService: UserService)
{

}
