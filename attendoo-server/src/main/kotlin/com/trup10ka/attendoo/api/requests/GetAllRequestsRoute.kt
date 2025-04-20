package com.trup10ka.attendoo.api.requests

import com.trup10ka.attendoo.ERROR_JSON_FIELD_NAME
import com.trup10ka.attendoo.GET_ALL_REQUESTS_ENDPOINT
import com.trup10ka.attendoo.api.attendooDepartment
import com.trup10ka.attendoo.data.Request
import com.trup10ka.attendoo.data.User
import com.trup10ka.attendoo.db.client.DbClient
import com.trup10ka.attendoo.db.dbQuery
import io.github.oshai.kotlinlogging.KotlinLogging
import io.ktor.http.HttpStatusCode
import io.ktor.server.auth.jwt.JWTPrincipal
import io.ktor.server.auth.principal
import io.ktor.server.plugins.origin
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get

private val logger = KotlinLogging.logger {}

fun Route.routeGetAllRequests(dbClient: DbClient)
{
    get(GET_ALL_REQUESTS_ENDPOINT) {
        logger.info { "Received request for GET_ALL_REQUESTS from ${call.request.origin.remoteHost}:${call.request.origin.remotePort}" }
        
        val principal = call.principal<JWTPrincipal>()
        if (principal == null)
        {
            call.respond(HttpStatusCode.Unauthorized, mapOf(ERROR_JSON_FIELD_NAME to "Authentication required"))
            logger.warn { "Unauthenticated user tried to get all requests from ${call.request.origin.remoteHost}:${call.request.origin.remotePort}" }
            return@get
        }
        
        val proposals = try
        {
            dbQuery {
                dbClient.proposalService.getAllProposals()
            }
        }
        catch (e: Exception)
        {
            logger.error(e) { "Failed to get proposals from database" }
            call.respond(HttpStatusCode.InternalServerError, mapOf(ERROR_JSON_FIELD_NAME to "Failed to get requests"))
            return@get
        }
        
        val requests = proposals.map { proposal ->
            val user = dbQuery {
                dbClient.userService.getUserById(proposal.attendooProposalId)
            }
            
            if (user == null)
            {
                logger.warn { "User not found for proposal ${proposal.id.value}" }
                return@map null
            }
            
            val descriptionParts = proposal.description.split(", Note: ")
            val company = if (descriptionParts.isNotEmpty()) descriptionParts[0].removePrefix("Company: ") else ""
            val note = if (descriptionParts.size > 1) descriptionParts[1] else ""
            
            Request(
                user = User(
                    firstName = user.name,
                    lastName = user.surname,
                    attendooUsername = user.attendooUsername,
                    attendooPassword = "",
                    email = user.email,
                    phoneNumber = user.phone,
                    role = user.role.toString(),
                    userStatus = user.defaultStatus.toString(),
                    userDepartment = user.department.toString(),
                    userDepartments = null
                ),
                company = company,
                note = note,
                status = proposal.proposedUserStatus.name
            )
        }.filterNotNull()
        
        logger.info { "Successfully retrieved ${requests.size} requests" }
        call.respond(HttpStatusCode.OK, requests)
    }
}
