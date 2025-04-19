package com.trup10ka.attendoo.api.auth

import com.trup10ka.attendoo.ERROR_JSON_FIELD_NAME
import com.trup10ka.attendoo.SUCCESS_JSON_FIELD_NAME
import com.trup10ka.attendoo.api.attendooDepartment
import com.trup10ka.attendoo.api.attendooRole
import com.trup10ka.attendoo.api.attendooUsername
import com.trup10ka.attendoo.db.client.DbClient
import com.trup10ka.attendoo.db.dao.User
import com.trup10ka.attendoo.db.dao.UserDepartment
import com.trup10ka.attendoo.db.dbQuery
import com.trup10ka.attendoo.db.tables.Users
import com.trup10ka.attendoo.dto.UserDTO
import com.trup10ka.attendoo.security.PasswordEncryptor
import io.github.oshai.kotlinlogging.KotlinLogging
import io.ktor.http.HttpStatusCode
import io.ktor.server.auth.jwt.JWTPrincipal
import io.ktor.server.auth.principal
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

        val principal = call.principal<JWTPrincipal>()
        if (principal == null)
        {
            call.respond(HttpStatusCode.Unauthorized, mapOf(ERROR_JSON_FIELD_NAME to "Authentication required"))
            logger.warn { "Unauthenticated user tried to register a new user from ${call.request.origin.remoteHost}:${call.request.origin.remotePort}" }
            return@post
        }

        val adminRole = principal.attendooRole
        if (!adminRole.equals("admin", ignoreCase = true))
        {
            call.respond(HttpStatusCode.Forbidden, mapOf(ERROR_JSON_FIELD_NAME to "Only admins can create users"))
            logger.warn { "Non-admin user tried to register a new user from ${call.request.origin.remoteHost}:${call.request.origin.remotePort}" }
            return@post
        }

        val adminUsername = principal.attendooUsername
        val adminDepartment = principal.attendooDepartment

        val userDTO = call.receive<UserDTO>()

        if (userDTO.attendooUsername == null || userDTO.attendooPassword == null || userDTO.userDepartment == null)
        {
            call.respond(
                HttpStatusCode.BadRequest,
                mapOf(ERROR_JSON_FIELD_NAME to "Missing username, password, or department")
            )
            logger.warn { "Received invalid user data from ${call.request.origin.remoteHost}:${call.request.origin.remotePort}" }
            return@post
        }

        val userDepartment = userDTO.userDepartment!!
        val userRole = userDTO.role

        // Handle department creation/validation
        if (!userDepartment.equals(adminDepartment, ignoreCase = true))
        {
            val departmentExists = dbQuery {
                dbClient.userDepartmentService.getDepartmentByName(userDepartment) != null
            }

            if (!departmentExists)
            {
                dbQuery {
                    val newDepartment = dbClient.userDepartmentService.createDepartment(userDepartment)

                    val adminUser = dbClient.userService.getUserByUsername(adminUsername)

                    if (adminUser != null)
                    {
                        dbClient.userDepartmentService.assignDepartmentToUser(
                            adminUser.id.value,
                            newDepartment.id.value
                        )
                    }
                }
            }
            else
            {
                val hasAccess = dbQuery {
                    val adminUser = dbClient.userService.getUserByUsername(adminUsername)
                    adminUser?.getAllDepartments()?.any { it.name.equals(userDepartment, ignoreCase = true) } ?: false
                }

                if (!hasAccess)
                {
                    call.respond(
                        HttpStatusCode.Forbidden,
                        mapOf(ERROR_JSON_FIELD_NAME to "You don't have access to this department")
                    )
                    logger.warn { "Admin tried to create a user in a department they don't have access to from ${call.request.origin.remoteHost}:${call.request.origin.remotePort}" }
                    return@post
                }
            }
        }

        // Handle role creation/validation
        if (userRole != null) {
            val roleExists = dbQuery {
                dbClient.roleService.getRoleByName(userRole) != null
            }

            if (!roleExists) {
                dbQuery {
                    dbClient.roleService.createRole(userRole)
                }
                logger.info { "Created new role: $userRole from ${call.request.origin.remoteHost}:${call.request.origin.remotePort}" }
            }
        }

        val encryptedPassword = passwordEncryptor.encrypt(userDTO.attendooPassword!!)

        val wasCreated =
            dbQuery { dbClient.userService.createUser(userDTO.convertToDTOWithEncryptedPassword(encryptedPassword)) }

        if (wasCreated)
        {
            call.respond(HttpStatusCode.Created, mapOf(SUCCESS_JSON_FIELD_NAME to true))
            logger.info { "Successfully created user from ${call.request.origin.remoteHost}:${call.request.origin.remotePort}" }
        }
        else
        {
            call.respond(HttpStatusCode.Conflict, mapOf(ERROR_JSON_FIELD_NAME to "User already exists"))
            logger.warn { "Failed to create user from ${call.request.origin.remoteHost}:${call.request.origin.remotePort}" }
        }
    }
}
