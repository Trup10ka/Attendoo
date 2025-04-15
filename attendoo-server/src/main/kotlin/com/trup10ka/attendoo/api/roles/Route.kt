package com.trup10ka.attendoo.api.roles

import com.trup10ka.attendoo.ROLES_ENDPOINT
import com.trup10ka.attendoo.db.client.DbClient
import io.ktor.server.routing.Route
import io.ktor.server.routing.route

fun Route.routeRoles(dbClient: DbClient)
{
    route(ROLES_ENDPOINT)
    {
        routeGetRoles(dbClient)
    }
}
