package com.trup10ka.attendoo.api.requests

import com.trup10ka.attendoo.CREATE_REQUEST_ENDPOINT
import com.trup10ka.attendoo.ERROR_JSON_FIELD_NAME
import com.trup10ka.attendoo.SUCCESS_JSON_FIELD_NAME
import com.trup10ka.attendoo.api.attendooUsername
import com.trup10ka.attendoo.data.Request
import com.trup10ka.attendoo.data.User
import com.trup10ka.attendoo.db.client.DbClient
import com.trup10ka.attendoo.db.dbQuery
import com.trup10ka.attendoo.dto.ProposalDTO
import com.trup10ka.attendoo.mail.EmailService
import com.trup10ka.attendoo.util.convertToKotlinxDateTime
import java.time.LocalDateTime
import io.github.oshai.kotlinlogging.KotlinLogging
import io.ktor.http.HttpStatusCode
import io.ktor.server.auth.jwt.JWTPrincipal
import io.ktor.server.auth.principal
import io.ktor.server.plugins.origin
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.post

private val logger = KotlinLogging.logger {}

data class RequestDTO(
    val company: String,
    val note: String
)

fun Route.routeCreateRequest(dbClient: DbClient, emailService: EmailService)
{
    post(CREATE_REQUEST_ENDPOINT) {
        logger.info { "Received request for CREATE_REQUEST from ${call.request.origin.remoteHost}:${call.request.origin.remotePort}" }

        val principal = call.principal<JWTPrincipal>()
        if (principal == null)
        {
            call.respond(HttpStatusCode.Unauthorized, mapOf(ERROR_JSON_FIELD_NAME to "Authentication required"))
            logger.warn { "Unauthenticated user tried to create a request from ${call.request.origin.remoteHost}:${call.request.origin.remotePort}" }
            return@post
        }

        val username = principal.attendooUsername

        val requestDTO = try
        {
            call.receive<RequestDTO>()
        }
        catch (e: Exception)
        {
            call.respond(HttpStatusCode.BadRequest, mapOf(ERROR_JSON_FIELD_NAME to "Invalid request data"))
            logger.warn { "Received invalid request data from ${call.request.origin.remoteHost}:${call.request.origin.remotePort}" }
            return@post
        }
        

        val user = dbQuery {
            dbClient.userService.getUserByUsername(username)
        }

        if (user == null)
        {
            call.respond(HttpStatusCode.NotFound, mapOf(ERROR_JSON_FIELD_NAME to "User not found"))
            logger.warn { "User not found for username $username from ${call.request.origin.remoteHost}:${call.request.origin.remotePort}" }
            return@post
        }

        // Create a Request object from the DTO and user
        val request = Request(
            user = User(
                firstName = user.name,
                lastName = user.surname,
                attendooUsername = user.attendooUsername,
                attendooPassword = "",
                email = user.email,
                phoneNumber = user.phone,
                role = user.role.toString(),
                userStatus = user.defaultStatus.toString(),
                userDepartment = user.department.toString()
            ),
            company = requestDTO.company,
            note = requestDTO.note,
            status = "pending"
        )

        // Save the request to the database as a proposal
        try {
            val currentTime = LocalDateTime.now().convertToKotlinxDateTime()
            val proposalDTO = ProposalDTO(
                name = "Request from ${user.name} ${user.surname}",
                description = "Company: ${requestDTO.company}, Note: ${requestDTO.note}",
                attendooProposalId = user.id.value,
                createdAt = currentTime,
                resolvedAt = null,
                currentStatus = user.defaultStatus.toString(),
                proposedStatus = "pending"
            )

            dbQuery {
                dbClient.proposalService.createProposal(proposalDTO)
            }

            logger.info { "Successfully saved request to database as proposal for user ${user.attendooUsername}" }
        } catch (e: Exception) {
            logger.error(e) { "Failed to save request to database as proposal for user ${user.attendooUsername}" }
            call.respond(HttpStatusCode.InternalServerError, mapOf(ERROR_JSON_FIELD_NAME to "Failed to save request"))
            return@post
        }

        // Send email notification
        val emailSent = emailService.sendRequestCreatedEmail(request)
        if (emailSent)
        {
            logger.info { "Request creation email sent to ${request.user.email}" }
        }
        else
        {
            logger.warn { "Failed to send request creation email to ${request.user.email}" }
        }

        call.respond(HttpStatusCode.Created, mapOf(SUCCESS_JSON_FIELD_NAME to true))
        logger.info { "Successfully created request from ${call.request.origin.remoteHost}:${call.request.origin.remotePort}" }
    }
}
