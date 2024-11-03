package com.trup10ka.attendoo.plugins

import com.trup10ka.attendoo.config.ConfigDistributor.config
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.*
import kotlinx.serialization.Serializable

fun Application.configureSecurity()
{
    if (config.isSecureConnection)
    {
        @Serializable
        data class MySession(val count: Int = 0)
        install(Sessions)
        {
            cookie<MySession>("MY_SESSION")
            {
                cookie.extensions["SameSite"] = "lax"
            }
        }
        routing {
            get("/session/increment") {
                val session = call.sessions.get<MySession>() ?: MySession()
                call.sessions.set(session.copy(count = session.count + 1))
                call.respondText("Counter is ${session.count}. Refresh to increment.")
            }
        }
    }
}
