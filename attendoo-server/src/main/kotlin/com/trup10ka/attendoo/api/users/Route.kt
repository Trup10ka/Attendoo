package com.trup10ka.attendoo.api.users

import com.trup10ka.attendoo.USERS_ENDPOINT
import com.trup10ka.attendoo.db.services.UserService
import io.ktor.server.routing.Route
import io.ktor.server.routing.route

fun Route.routeUsers(userService: UserService)
{
    route(USERS_ENDPOINT) {
        routeEditUser(userService)
        routeGetAllUsersFromDepartment(userService)
    }
}
