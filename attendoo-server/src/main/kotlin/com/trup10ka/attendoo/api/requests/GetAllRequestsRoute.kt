package com.trup10ka.attendoo.api.requests

import com.trup10ka.attendoo.ERROR_JSON_FIELD_NAME
import com.trup10ka.attendoo.GET_ALL_REQUESTS_ENDPOINT
import com.trup10ka.attendoo.api.attendooDepartment
import com.trup10ka.attendoo.data.Request
import com.trup10ka.attendoo.data.User
import com.trup10ka.attendoo.db.client.DbClient
import com.trup10ka.attendoo.db.dbQuery
import com.trup10ka.attendoo.db.toDTO
import com.trup10ka.attendoo.util.convertToKotlinxDateTime
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

        val requests = try
        {
            dbQuery {
                val proposals = dbClient.proposalService.getAllProposals()
                proposals.map { proposal ->
                    val descriptionParts = proposal.description.split(", Note: ")
                    val company = if (descriptionParts.isNotEmpty()) descriptionParts[0].removePrefix("Company: ") else ""
                    val note = if (descriptionParts.size > 1) descriptionParts[1] else ""

                    Request(
                        proposer = User(
                            firstName = proposal.proposer.name,
                            lastName = proposal.proposer.surname,
                            attendooUsername = proposal.proposer.attendooUsername,
                            attendooPassword = "",
                            email = proposal.proposer.email,
                            phoneNumber = proposal.proposer.phone,
                            role = proposal.proposer.role.toString(),
                            userStatus = proposal.proposer.defaultStatus.toString(),
                            userDepartment = proposal.proposer.department.toString(),
                            userDepartments = null
                        ),
                        proposed = User(
                            firstName = proposal.proposed.name,
                            lastName = proposal.proposed.surname,
                            attendooUsername = proposal.proposed.attendooUsername,
                            attendooPassword = "",
                            email = proposal.proposed.email,
                            phoneNumber = proposal.proposed.phone,
                            role = proposal.proposed.role.toString(),
                            userStatus = proposal.proposed.defaultStatus.toString(),
                            userDepartment = proposal.proposed.department.toString(),
                            userDepartments = null
                        ),
                        proposedDepartment = proposal.proposed.department.name,
                        note = note,
                        currentStatus = proposal.currentUserStatus.name,
                        proposedStatus = proposal.proposedUserStatus.name,
                        resolvedAt = proposal.resolvedAt?.convertToKotlinxDateTime()
                    )
                }
            }
        }
        catch (e: Exception)
        {
            logger.error(e) { "Failed to get proposals from database" }
            call.respond(HttpStatusCode.InternalServerError, mapOf(ERROR_JSON_FIELD_NAME to "Failed to get requests"))
            return@get
        }

        logger.info { "Successfully retrieved ${requests.size} requests" }
        call.respond(HttpStatusCode.OK, requests)
    }
}
