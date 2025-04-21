package com.trup10ka.attendoo.api.statuses

import com.trup10ka.attendoo.STATUSES_ENDPOINT
import com.trup10ka.attendoo.db.services.UserStatusService
import io.ktor.server.routing.Route
import io.ktor.server.routing.route

fun Route.routeStatuses(userStatusService: UserStatusService)
{
    route(STATUSES_ENDPOINT)
    {
        routeGetAllStatuses(userStatusService)
    }
}
