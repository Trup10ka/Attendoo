package com.trup10ka.attendoo.api.auth

import com.trup10ka.attendoo.db.client.DbClient
import com.trup10ka.attendoo.db.dbQuery
import com.trup10ka.attendoo.dto.UserDTO
import com.trup10ka.attendoo.security.PasswordEncryptor
import io.github.oshai.kotlinlogging.KotlinLogging
import io.ktor.http.HttpStatusCode
import io.ktor.server.plugins.origin
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.post

val logger = KotlinLogging.logger { }

// Note that user creation is done by administrator only, so he is not supposed to receive token after registration
fun Route.routeRegister(dbClient: DbClient, passwordEncryptor: PasswordEncryptor)
{
    post("/register") {
        logger.info { "Received request for REGISTER from ${call.request.origin.remoteHost}:${call.request.origin.remotePort}" }
        
        val userDTO = call.receive<UserDTO>()
        
        if (userDTO.attendooUsername == null || userDTO.attendooPassword == null)
        {
            call.respond(mapOf("error" to "Missing username or password"))
            logger.warn { "Received invalid username or password from ${call.request.origin.remoteHost}:${call.request.origin.remotePort}" }
            return@post
        }
        val encryptedPassword = passwordEncryptor.encrypt(userDTO.attendooPassword!!)
        
        val wasCreated =
            dbQuery { dbClient.userService.createUser(userDTO.convertToDTOWithEncryptedPassword(encryptedPassword)) }
        
        if (wasCreated)
        {
            call.respond(HttpStatusCode.Created, mapOf("success" to true))
            logger.info { "Successfully created user from ${call.request.origin.remoteHost}:${call.request.origin.remotePort}" }
        }
        else
        {
            call.respond(HttpStatusCode.Conflict, mapOf("error" to "User already exists"))
            logger.warn { "Failed to create user from ${call.request.origin.remoteHost}:${call.request.origin.remotePort}" }
        }
        
        call.respond(HttpStatusCode.Processing)
    }
}
