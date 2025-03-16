package com.trup10ka.attendoo.plugins.routing

import com.trup10ka.attendoo.api.attendances.routeAttendances
import com.trup10ka.attendoo.api.auth.routeAuth
import com.trup10ka.attendoo.api.requests.routeRequests
import com.trup10ka.attendoo.api.users.routeUsers
import com.trup10ka.attendoo.db.client.DbClient
import io.ktor.server.auth.authenticate
import io.ktor.server.routing.Route

fun Route.routeAPICalls(dbClient: DbClient)
{
    routeAuth(dbClient)
    
    // TODO: Add ATTENDANCES, CREATE_USER, REQUESTS api routes
    authenticate {
        routeUsers(dbClient.userService)
        routeAttendances(dbClient.attendanceService, dbClient.userService)
        routeRequests(dbClient)
    }
}
