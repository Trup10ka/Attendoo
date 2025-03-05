package com.trup10ka.attendoo.plugins.routing.api

import com.trup10ka.attendoo.data.AuthCredentials
import com.trup10ka.attendoo.db.client.DbClient
import com.trup10ka.attendoo.dto.UserDTO
import com.trup10ka.attendoo.security.PasswordEncryptor
import com.trup10ka.attendoo.security.Sha384PasswordEncryptor
import com.trup10ka.attendoo.util.launchIOCoroutine
import io.ktor.server.request.receiveParameters
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.post
import io.ktor.server.routing.route

fun Route.routeAuth(dbClient: DbClient)
{
    val passwordEncryptor: PasswordEncryptor = Sha384PasswordEncryptor()
    route("/auth") {
        routeLogin(dbClient, passwordEncryptor)
        routeRegister(dbClient, passwordEncryptor)
    }
}

fun Route.routeLogin(dbClient: DbClient, passwordEncryptor: PasswordEncryptor)
{
    post("/login") {
        val params = call.receiveParameters()
        val authCredentials = AuthCredentials(
            params["username"],
            params["password"]
        )
        
        if (authCredentials.username == null || authCredentials.password == null)
        {
            call.respond(mapOf("error" to "Missing username or password"))
            return@post
        }
        
        launchIOCoroutine {
            val user = dbClient.userService.getUserByUsername(authCredentials.username!!)
            if (user == null || user.attendooPassword != passwordEncryptor.encrypt(authCredentials.password!!))
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
fun  Route.routeRegister(dbClient: DbClient, passwordEncryptor: PasswordEncryptor)
{
    post("/register") {
        val params = call.receiveParameters()
        val firstName = params["first-name"]
        val lastName = params["last-name"]
        val username = params["username"]
        var password = params["password"]
        
        if (username == null || password == null)
        {
            call.respond(mapOf("error" to "Missing username or password"))
            return@post
        }
        
        password = passwordEncryptor.encrypt(password)
        
        launchIOCoroutine {
            val wasCreated = dbClient.userService.createUser(UserDTO(firstName, lastName, username, password, ))
        }
    }
}
