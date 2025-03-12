package com.trup10ka.attendoo.api.auth

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.trup10ka.attendoo.TOKEN_NAME
import com.trup10ka.attendoo.config.ConfigDistributor.config
import com.trup10ka.attendoo.data.AuthCredentials
import com.trup10ka.attendoo.db.client.DbClient
import com.trup10ka.attendoo.db.dbQuery
import com.trup10ka.attendoo.security.PasswordEncryptor
import com.trup10ka.attendoo.util.launchIOCoroutine
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
            call.respond(mapOf("error" to "Missing username or password"))
            return@post
        }
        
        launchIOCoroutine {
            val user = dbQuery { dbClient.userService.getUserByUsername(authCredentials.username!!) }
            
            if (user == null || user.attendooPassword != passwordEncryptor.encrypt(authCredentials.password!!))
            {
                call.respond(mapOf("error" to "Invalid username or password"))
            }
            else
            {
                call.respond(mapOf(TOKEN_NAME to JWT.create()
                        .withAudience(config.jwt.audience)
                        .withIssuer(config.jwt.issuer)
                        .withClaim("attendooUsername", authCredentials.username!!)
                        .withClaim("attendooRole", user.role.name)
                        .withExpiresAt(Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24))
                        .sign(Algorithm.HMAC512(config.jwt.secret))
                    )
                )
            }
        }
    }
}
