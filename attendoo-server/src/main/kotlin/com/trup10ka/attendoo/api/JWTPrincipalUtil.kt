package com.trup10ka.attendoo.api

import com.trup10ka.attendoo.JWT_DEPARTMENT_FIELD
import com.trup10ka.attendoo.JWT_ROLE_FIELD
import com.trup10ka.attendoo.JWT_USERNAME_FIELD
import io.ktor.server.auth.jwt.JWTCredential
import io.ktor.server.auth.jwt.JWTPrincipal

val JWTPrincipal.attendooUsername: String
    get() = payload.getClaim(JWT_USERNAME_FIELD).asString()

val JWTPrincipal.attendooRole: String
    get() = payload.getClaim(JWT_ROLE_FIELD).asString()

val JWTPrincipal.attendooDepartment: String
    get() = payload.getClaim(JWT_DEPARTMENT_FIELD).asString()
