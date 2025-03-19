package com.trup10ka.attendoo.plugins.security

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.exceptions.JWTVerificationException
import com.auth0.jwt.exceptions.TokenExpiredException
import com.trup10ka.attendoo.api.users.logger
import com.trup10ka.attendoo.config.ConfigDistributor.config
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.auth.Authentication
import io.ktor.server.auth.jwt.JWTCredential
import io.ktor.server.auth.jwt.JWTPrincipal
import io.ktor.server.auth.jwt.jwt
import io.ktor.server.plugins.origin
import io.ktor.server.response.respond

val JWTCredential.attendooUsername: String?
    get() = payload.getClaim("attendooUsername").asString()

val JWTCredential.attendooRole: String?
    get() = payload.getClaim("attendooRole").asString()

fun Application.configureJWT()
{
    install(Authentication)
    {
        val jwtVerifier = JWT
            .require(Algorithm.HMAC512(config.jwt.secret))
            .withAudience(config.jwt.audience)
            .withIssuer(config.jwt.issuer)
            .build()
        
        jwt {
            verifier(jwtVerifier)
            
            validate { credential ->
                if (credential.attendooUsername != null && credential.attendooRole != null)
                    JWTPrincipal(credential.payload)
                else
                    null
            }
            challenge { _, _ ->
                logger.warn { "Authentication failed for ${call.request.origin.remoteHost}:${call.request.origin.remotePort}" }
                val token = call.request.headers["Authorization"]?.removePrefix("Bearer ")
                
                if (token == null)
                {
                    call.respond(HttpStatusCode.Unauthorized, mapOf("error" to "No token provided"))
                    return@challenge
                }
                
                try
                {
                    val decodedToken = jwtVerifier.verify(token)
                    println(decodedToken)
                }
                catch (_: TokenExpiredException)
                {
                    call.respond(HttpStatusCode.Unauthorized, mapOf("error" to "Token expired"))
                }
                catch (_: JWTVerificationException)
                {
                    call.respond(HttpStatusCode.Unauthorized, mapOf("error" to "Invalid token"))
                }
            }
        }
    }
}
