package com.trup10ka.attendoo.api.requests

import com.trup10ka.attendoo.db.client.DbClient
import io.ktor.server.routing.Route
import io.ktor.server.routing.route

fun Route.routeRequests(dbClient: DbClient)
{
    route("/requests") {
        routeGetAllRequests(dbClient)
        routeGetUserRequests(dbClient)
        routeCreateRequest(dbClient)
        routeUpdateRequest(dbClient)
        routeDeleteRequest(dbClient)
    }
}
