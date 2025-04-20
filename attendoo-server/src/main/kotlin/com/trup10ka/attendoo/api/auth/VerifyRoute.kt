package com.trup10ka.attendoo.api.auth

import com.trup10ka.attendoo.DEPARTMENT_JSON_FIELD
import com.trup10ka.attendoo.ERROR_JSON_FIELD_NAME
import com.trup10ka.attendoo.JWT_DEPARTMENT_FIELD
import com.trup10ka.attendoo.JWT_ROLE_FIELD
import com.trup10ka.attendoo.JWT_USERNAME_FIELD
import com.trup10ka.attendoo.ROLE_JSON_FIELD
import com.trup10ka.attendoo.USERNAME_JSON_FIELD
import io.ktor.http.HttpStatusCode
import io.ktor.server.auth.jwt.JWTPrincipal
import io.ktor.server.auth.principal
import io.ktor.server.plugins.origin
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get

fun Route.routeVerify()
{
    get("/verify")
    {
        logger.info { "Received request for VERIFY from ${call.request.origin.remoteHost}:${call.request.origin.remotePort}" }

        val principal = call.principal<JWTPrincipal>()
        val username = principal?.payload?.getClaim(JWT_USERNAME_FIELD)?.asString()
        val role = principal?.payload?.getClaim(JWT_ROLE_FIELD)?.asString()
        val department = principal?.payload?.getClaim(JWT_DEPARTMENT_FIELD)?.asString()

        if (username != null && role != null)
        {
            val responseMap = mutableMapOf(
                USERNAME_JSON_FIELD to username,
                ROLE_JSON_FIELD to role
            )

            // Add department if available
            if (department != null) {
                responseMap[DEPARTMENT_JSON_FIELD] = department
            }

            call.respond(HttpStatusCode.OK, responseMap)
        }
        else
        {
            call.respond(HttpStatusCode.Unauthorized, mapOf(ERROR_JSON_FIELD_NAME to "Invalid token"))
        }
    }
}
