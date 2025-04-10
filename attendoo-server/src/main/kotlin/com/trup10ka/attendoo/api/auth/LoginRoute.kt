package com.trup10ka.attendoo.api.auth

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.trup10ka.attendoo.ERROR_JSON_FIELD_NAME
import com.trup10ka.attendoo.JWT_ROLE_FIELD
import com.trup10ka.attendoo.JWT_USERNAME_FIELD
import com.trup10ka.attendoo.STATUS_NAME
import com.trup10ka.attendoo.TOKEN_NAME
import com.trup10ka.attendoo.config.ConfigDistributor.config
import com.trup10ka.attendoo.data.AuthCredentials
import com.trup10ka.attendoo.db.client.DbClient
import com.trup10ka.attendoo.db.dbQuery
import com.trup10ka.attendoo.db.toDTO
import com.trup10ka.attendoo.dto.UserDTO
import com.trup10ka.attendoo.security.PasswordEncryptor
import io.ktor.http.HttpStatusCode
import io.ktor.server.plugins.origin
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.post
import java.util.Date

fun Route.routeLogin(dbClient: DbClient, passwordEncryptor: PasswordEncryptor)
{
    post("/login") {
        logger.info { "Received request for LOGIN from ${call.request.origin.remoteHost}:${call.request.origin.remotePort}" }
        
        val authCredentials = call.receive<AuthCredentials>()
        
        if (authCredentials.username == null || authCredentials.password == null)
        {
            call.respond(HttpStatusCode.Unauthorized, mapOf(ERROR_JSON_FIELD_NAME to "Missing username or password"))
            return@post
        }
        
        
        val user = dbQuery { dbClient.userService.getUserByUsername(authCredentials.username!!)?.toDTO() }
        
        if (user == null || user.attendooPassword != passwordEncryptor.encrypt(authCredentials.password!!))
        {
            call.respond(HttpStatusCode.Unauthorized, mapOf(ERROR_JSON_FIELD_NAME to "Invalid username or password"))
        }
        else
        {
            call.respond(
                mapOf(
                    TOKEN_NAME to JWT.create()
                        .withAudience(config.jwt.audience)
                        .withIssuer(config.jwt.issuer)
                        .withClaim(JWT_USERNAME_FIELD, authCredentials.username!!)
                        .withClaim(JWT_ROLE_FIELD, user.role)
                        .withExpiresAt(Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24))
                        .sign(Algorithm.HMAC512(config.jwt.secret)),
                    STATUS_NAME to user.userStatus
                )
            )
        }
        
    }
}
