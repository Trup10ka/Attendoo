package com.trup10ka.attendoo.api.requests

import com.trup10ka.attendoo.REQUESTS_ENDPOINT
import com.trup10ka.attendoo.db.client.DbClient
import com.trup10ka.attendoo.mail.EmailService
import io.ktor.server.routing.Route
import io.ktor.server.routing.route

fun Route.routeRequests(dbClient: DbClient, emailService: EmailService)
{
    route(REQUESTS_ENDPOINT) {
        routeGetAllRequests(dbClient)
        routeGetUserRequests(dbClient)
        routeCreateRequest(dbClient, emailService)
        routeUpdateRequest(dbClient)
        routeDeleteRequest(dbClient)
    }
}
