package com.trup10ka.attendoo.api.users

import com.trup10ka.attendoo.db.client.DbClient
import io.ktor.server.routing.Route
import io.ktor.server.routing.get

fun Route.routeGetUsers(dbClient: DbClient)
{
    get("/getUsers")
    {
    
    }
}
