package com.trup10ka.attendoo.plugins.security

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.trup10ka.attendoo.config.ConfigDistributor.config
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.auth.Authentication
import io.ktor.server.auth.jwt.jwt

fun Application.configureJWT()
{
    install(Authentication)
    {
        jwt {
            verifier(JWT
                .require(Algorithm.HMAC256(config.jwt.secret))
                .withAudience(config.jwt.audience)
                .withIssuer(config.jwt.issuer)
                .build())
        }
    }
}
