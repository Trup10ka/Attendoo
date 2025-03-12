package com.trup10ka.attendoo.api.users

import com.trup10ka.attendoo.db.client.DbClient
import io.ktor.server.routing.Route
import io.ktor.server.routing.route

fun Route.routeUsers(dbClient: DbClient)
{
    route("/users") {
        routeEditUser(dbClient)
    }
}
