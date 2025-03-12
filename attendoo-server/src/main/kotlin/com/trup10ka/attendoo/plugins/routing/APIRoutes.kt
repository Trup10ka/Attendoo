package com.trup10ka.attendoo.plugins.routing

import com.trup10ka.attendoo.api.auth.routeAuth
import com.trup10ka.attendoo.api.users.routeUsers
import com.trup10ka.attendoo.db.client.DbClient
import io.ktor.server.auth.authenticate
import io.ktor.server.routing.Route

fun Route.routeAPICalls(dbClient: DbClient)
{
    routeAuth(dbClient)
    authenticate { routeUsers(dbClient) }
}
