package com.trup10ka.attendoo.api.auth

import com.trup10ka.attendoo.AUTH_ENDPOINT
import com.trup10ka.attendoo.db.client.DbClient
import com.trup10ka.attendoo.security.PasswordEncryptor
import com.trup10ka.attendoo.security.Sha384PasswordEncryptor
import com.trup10ka.attendoo.mail.EmailService
import io.ktor.server.auth.authenticate
import io.ktor.server.routing.Route
import io.ktor.server.routing.route

fun Route.routeAuth(dbClient: DbClient, emailService: EmailService)
{
    val passwordEncryptor: PasswordEncryptor = Sha384PasswordEncryptor()
    route(AUTH_ENDPOINT) {

        routeLogin(dbClient, passwordEncryptor)

        authenticate {
            routeRegister(dbClient, passwordEncryptor, emailService)
            routeVerify()
        }
    }
}
