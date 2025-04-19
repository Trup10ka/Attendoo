package com.trup10ka.attendoo.api.department

import com.trup10ka.attendoo.DEPARTMENTS_ENDPOINT
import com.trup10ka.attendoo.db.services.UserDepartmentService
import io.ktor.server.routing.Route
import io.ktor.server.routing.route

fun Route.routeDepartments(userDepartmentService: UserDepartmentService)
{
    route(DEPARTMENTS_ENDPOINT)
    {
        routeGetAllDepartments()
    }
}
