package com.trup10ka.attendoo.plugins.routing.api

import com.trup10ka.attendoo.db.client.DbClient
import io.ktor.server.response.respondText
import io.ktor.server.routing.Route
import io.ktor.server.routing.get

fun Route.routeAPICalls(dbClient: DbClient)
{
    routeAuth(dbClient)
}
