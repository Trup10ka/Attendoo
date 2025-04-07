package com.trup10ka.attendoo

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.trup10ka.attendoo.config.ConfigDistributor.config
import java.util.Date

fun main()
{
    val jwtVerifier = JWT.create()
        .withAudience(config.jwt.audience)
        .withIssuer(config.jwt.issuer)
        .withClaim("attendooUsername", "admin")
        .withClaim("attendooRole", "admin")
        .withExpiresAt(Date(System.currentTimeMillis() + 1000000 * 60 * 60 * 24))
        .sign(Algorithm.HMAC512(config.jwt.secret))
    
    println(jwtVerifier)
    
    val attendoo = Attendoo()
    attendoo.init()
    attendoo.startApp()
}
