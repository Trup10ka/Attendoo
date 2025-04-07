package com.trup10ka.attendoo.api.auth

import com.trup10ka.attendoo.db.client.DbClient
import com.trup10ka.attendoo.security.PasswordEncryptor
import com.trup10ka.attendoo.security.Sha384PasswordEncryptor
import io.ktor.server.auth.authenticate
import io.ktor.server.routing.Route
import io.ktor.server.routing.route

fun Route.routeAuth(dbClient: DbClient)
{
    val passwordEncryptor: PasswordEncryptor = Sha384PasswordEncryptor()
    route("/auth") {
        
        routeLogin(dbClient, passwordEncryptor)
        
        authenticate {
            routeRegister(dbClient, passwordEncryptor)
            routeVerify()
        }
    }
}
