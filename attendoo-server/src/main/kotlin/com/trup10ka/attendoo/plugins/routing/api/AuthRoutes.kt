package com.trup10ka.attendoo.plugins.routing.api

import com.trup10ka.attendoo.db.client.DbClient
import com.trup10ka.attendoo.dto.UserDTO
import com.trup10ka.attendoo.util.launchIOCoroutine
import io.ktor.server.request.receiveParameters
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.post
import io.ktor.server.routing.route

fun Route.routeAuth(dbClient: DbClient)
{
    route("/auth") {
        routeLogin(dbClient)
        routeRegister(dbClient)
    }
}

fun Route.routeLogin(dbClient: DbClient)
{
    post("/login") {
        val params = call.receiveParameters()
        val username = params["username"]
        val password = params["password"]
        
        if (username == null || password == null)
        {
            call.respond(mapOf("error" to "Missing username or password"))
            return@post
        }
        
        launchIOCoroutine {
            val user = dbClient.userService.getUserByUsername(username)
            if (user == null || user.attendooPassword != password)
            {
                call.respond(mapOf("error" to "Invalid username or password"))
            }
            else
            {
                call.respond(mapOf("success" to "Logged in"))
                // respond with JWT
            }
        }
    }
}

// TODO: Implement encrypting password
fun  Route.routeRegister(dbClient: DbClient)
{
    post("/register") {
        val params = call.receiveParameters()
        val firstName = params["first-name"]
        val lastName = params["last-name"]
        
        val username = params["username"]
        val password = params["password"]
        
        if (username == null || password == null)
        {
            call.respond(mapOf("error" to "Missing username or password"))
            return@post
        }
        
        launchIOCoroutine {
            val wasCreated = dbClient.userService.createUser(UserDTO(firstName, lastName, username, password, ))
        }
    }
}
