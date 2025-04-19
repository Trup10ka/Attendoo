package com.trup10ka.attendoo.api.roles

import com.trup10ka.attendoo.ALL_ROLES_ENDPOINT
import com.trup10ka.attendoo.dto.RoleDTO
import com.trup10ka.attendoo.db.client.DbClient
import com.trup10ka.attendoo.db.dbQuery
import com.trup10ka.attendoo.db.toDTO
import io.ktor.http.HttpStatusCode
import io.ktor.server.response.respond
import io.ktor.server.response.respondText
import io.ktor.server.routing.Route
import io.ktor.server.routing.get

fun Route.routeGetRoles(dbClient: DbClient)
{
    get(ALL_ROLES_ENDPOINT)
    {
        val roles = dbQuery {
            dbClient.roleService.getAllRoles().map { it.toDTO() }
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
