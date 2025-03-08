package com.trup10ka.attendoo.plugins.routing.api

import com.trup10ka.attendoo.data.AuthCredentials
import com.trup10ka.attendoo.db.client.DbClient
import com.trup10ka.attendoo.db.dbQuery
import com.trup10ka.attendoo.dto.UserDTO
import com.trup10ka.attendoo.security.PasswordEncryptor
import com.trup10ka.attendoo.security.Sha384PasswordEncryptor
import com.trup10ka.attendoo.util.launchIOCoroutine
import io.github.oshai.kotlinlogging.KotlinLogging
import io.ktor.http.HttpStatusCode
import io.ktor.server.plugins.origin
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.post
import io.ktor.server.routing.route

val logger = KotlinLogging.logger { }

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
        logger.info { "Received request for LOGIN from ${call.request.origin.remoteHost}:${call.request.origin.remotePort}" }
        
        val authCredentials = call.receive<AuthCredentials>()
        
        if (authCredentials.username == null || authCredentials.password == null)
        {
            call.respond(mapOf("error" to "Missing username or password"))
            return@post
        }
        
        launchIOCoroutine {
            val user = dbQuery { dbClient.userService.getUserByUsername(authCredentials.username!!) }
            
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

fun Route.routeRegister(dbClient: DbClient, passwordEncryptor: PasswordEncryptor)
{
    post("/register") {
        logger.info { "Received request for REQUEST from ${call.request.origin.remoteHost}:${call.request.origin.remotePort}" }
        
        val userDTO = call.receive<UserDTO>()
        
        if (userDTO.attendooUsername == null || userDTO.attendooPassword == null)
        {
            call.respond(mapOf("error" to "Missing username or password"))
            logger.warn {  "Received invalid username or password from ${call.request.origin.remoteHost}:${call.request.origin.remotePort}" }
            return@post
        }
        val encryptedPassword = passwordEncryptor.encrypt(userDTO.attendooPassword!!)
        
        launchIOCoroutine {
            val wasCreated =
                dbQuery { dbClient.userService.createUser(userDTO.convertToDTOWithEncryptedPassword(encryptedPassword)) }
            
            if (wasCreated)
            {
                call.respond(HttpStatusCode.Created, mapOf("success" to "User created"))
                logger.info { "Successfully created user from ${call.request.origin.remoteHost}:${call.request.origin.remotePort}" }
            }
            else
            {
                call.respond(HttpStatusCode.Conflict, mapOf("error" to "User already exists"))
                logger.warn { "Failed to create user from ${call.request.origin.remoteHost}:${call.request.origin.remotePort}" }
            }
        }
        call.respond(HttpStatusCode.Processing)
    }
}
