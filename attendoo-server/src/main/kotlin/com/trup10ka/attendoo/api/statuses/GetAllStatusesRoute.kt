package com.trup10ka.attendoo.api.statuses

import com.trup10ka.attendoo.ALL_STATUSES_ENDPOINT
import com.trup10ka.attendoo.db.dbQuery
import com.trup10ka.attendoo.db.services.UserStatusService
import io.ktor.http.HttpStatusCode
import io.ktor.server.response.respond
import io.ktor.server.response.respondText
import io.ktor.server.routing.Route
import io.ktor.server.routing.get

fun Route.routeGetAllStatuses(userStatusService: UserStatusService)
{
    get(ALL_STATUSES_ENDPOINT)
    {
        val roles = dbQuery {
            userStatusService.getAllUserStatuses().map { it.name }
        }
        
        if (roles.isNotEmpty())
        {
            call.respond(HttpStatusCode.OK, roles)
        }
        else
        {
            call.respondText("No roles found", status = HttpStatusCode.NotFound)
        }
    }
}
