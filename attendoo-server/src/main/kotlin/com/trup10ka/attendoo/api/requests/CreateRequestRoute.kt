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
import com.trup10ka.attendoo.dto.RequestDTONoProposer
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
            call.receive<RequestDTONoProposer>()
        }
        catch (_: Exception)
        {
            call.respond(HttpStatusCode.BadRequest, mapOf(ERROR_JSON_FIELD_NAME to "Invalid request data"))
            logger.warn { "Received invalid request data from ${call.request.origin.remoteHost}:${call.request.origin.remotePort}" }
            return@post
        }
        
        
        val user = dbQuery {
            dbClient.userService.getUserByUsername(username)?.toDTOWithID()
        }
        
        if (user == null)
        {
            call.respond(HttpStatusCode.NotFound, mapOf(ERROR_JSON_FIELD_NAME to "User not found"))
            logger.warn { "User not found for username $username from ${call.request.origin.remoteHost}:${call.request.origin.remotePort}" }
            return@post
        }
        
        // Get the proposed user
        val proposedUser = dbQuery {
            dbClient.userService.getUserByUsername(requestDTO.proposed)?.toDTOWithID()
        }
        
        if (proposedUser == null)
        {
            call.respond(HttpStatusCode.NotFound, mapOf(ERROR_JSON_FIELD_NAME to "Proposed user not found"))
            logger.warn { "Proposed user not found for username ${requestDTO.proposed} from ${call.request.origin.remoteHost}:${call.request.origin.remotePort}" }
            return@post
        }
        
        // Create a Request object from the DTO and users
        val request = Request(
            proposer = User(
                firstName = user.firstName!!,
                lastName = user.lastName!!,
                attendooUsername = user.attendooUsername!!,
                attendooPassword = "",
                email = user.email!!,
                phoneNumber = user.phoneNumber!!,
                role = user.role.toString(),
                userStatus = user.userStatus.toString(),
                userDepartment = user.userDepartment.toString()
            ),
            proposed = User(
                firstName = proposedUser.firstName!!,
                lastName = proposedUser.lastName!!,
                attendooUsername = proposedUser.attendooUsername!!,
                attendooPassword = "",
                email = proposedUser.email!!,
                phoneNumber = proposedUser.phoneNumber!!,
                role = proposedUser.role.toString(),
                userStatus = proposedUser.userStatus.toString(),
                userDepartment = proposedUser.userDepartment.toString()
            ),
            proposedDepartment = proposedUser.userDepartment.toString(),
            note = "",
            currentStatus = user.userStatus.toString(),
            proposedStatus = requestDTO.proposedStatus,
        )
        
        try
        {
            val currentTime = LocalDateTime.now().convertToKotlinxDateTime()
            val proposalDTO = ProposalDTO(
                name = "Request from ${user.firstName} ${user.lastName}",
                description = "Department: ${proposedUser.userDepartment}",
                proposerId = user.id,
                proposedId = proposedUser.id,
                createdAt = currentTime,
                resolvedAt = null,
                currentStatus = user.userStatus.toString(),
                proposedStatus = requestDTO.proposedStatus
            )
            
            dbQuery {
                dbClient.proposalService.createProposal(proposalDTO)
            }
            
            logger.info { "Successfully saved request to database as proposal for user ${user.attendooUsername}" }
        }
        catch (e: Exception)
        {
            logger.error(e) { "Failed to save request to database as proposal for user ${user.attendooUsername}" }
            call.respond(HttpStatusCode.InternalServerError, mapOf(ERROR_JSON_FIELD_NAME to "Failed to save request"))
            return@post
        }
        call.respond(HttpStatusCode.Created, mapOf(SUCCESS_JSON_FIELD_NAME to true))
        // Send email notification
        val emailSent = emailService.sendRequestCreatedEmail(request)
        if (emailSent)
        {
            logger.info { "Request creation email sent to ${request.proposer.email}" }
        }
        else
        {
            logger.warn { "Failed to send request creation email to ${request.proposer.email}" }
        }
        logger.info { "Successfully created request from ${call.request.origin.remoteHost}:${call.request.origin.remotePort}" }
    }
}
