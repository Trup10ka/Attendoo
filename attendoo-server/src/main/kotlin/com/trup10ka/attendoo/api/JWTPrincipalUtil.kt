package com.trup10ka.attendoo.api

import io.ktor.server.auth.jwt.JWTCredential
import io.ktor.server.auth.jwt.JWTPrincipal

val JWTPrincipal.attendooUsername: String
    get() = payload.getClaim("attendooUsername").asString()

val JWTPrincipal.attendooRole: String
    get() = payload.getClaim("attendooRole").asString()
