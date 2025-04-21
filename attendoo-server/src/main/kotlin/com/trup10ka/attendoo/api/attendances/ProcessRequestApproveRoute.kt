package com.trup10ka.attendoo.api.attendances

import com.trup10ka.attendoo.ATTENDANCE_REQUEST_APPROVE
import com.trup10ka.attendoo.ATTENDANCE_REQUEST_REJECT
import com.trup10ka.attendoo.SUCCESS_JSON_FIELD_NAME
import com.trup10ka.attendoo.data.RequestApproval
import com.trup10ka.attendoo.db.dbQuery
import com.trup10ka.attendoo.db.services.ProposalService
import com.trup10ka.attendoo.db.services.UserAttendanceService
import com.trup10ka.attendoo.dto.UserAttendanceDTO
import com.trup10ka.attendoo.mail.EmailService
import com.trup10ka.attendoo.util.convertToKotlinxDateTime
import com.trup10ka.attendoo.util.toJavaDateTime
import io.github.oshai.kotlinlogging.KotlinLogging
import io.ktor.http.HttpStatusCode
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.post
import kotlinx.datetime.Clock

private val logger = KotlinLogging.logger {  }

fun Route.routesProcessRequestApprove(
    userAttendanceService: UserAttendanceService,
    userProposalService: ProposalService,
    emailService: EmailService
)
{
    post(ATTENDANCE_REQUEST_APPROVE)
    {
        val request = call.receive<RequestApproval>()
        
        val emailOfProposer = dbQuery {
            
            val proposalDao = userProposalService.getAllProposals().singleOrNull {
                it.proposer.attendooUsername.equals(request.userProposing, ignoreCase = true) &&
                        request.proposedStatus.equals(it.proposedUserStatus.name, ignoreCase = true) &&
                        it.currentUserStatus.name.equals(request.currentStatus, ignoreCase = true)
            }
            
            logger.debug { proposalDao }
            
            
            val startDate = Clock.System.now().toJavaDateTime()
            
            logger.debug { "Start date is $startDate" }
            
            val successful = userAttendanceService.createAttendance(
                UserAttendanceDTO(
                    userId = proposalDao!!.proposer.id.value,
                    startDate = startDate!!.convertToKotlinxDateTime().date,
                    endDate = proposalDao.resolvedAt?.convertToKotlinxDateTime()?.date,
                    userStatusId = proposalDao.proposedUserStatus.id.value
                )
            )
            
            if (successful)
            {
                userProposalService.deleteProposal(proposalDao.id.value)
                call.respond(HttpStatusCode.OK, mapOf(SUCCESS_JSON_FIELD_NAME to true))
            }
            else
            {
                call.respond(HttpStatusCode.InternalServerError, mapOf(SUCCESS_JSON_FIELD_NAME to false))
            }
            return@dbQuery proposalDao.proposer.email
        }
        emailService.sendEmail(emailOfProposer, "Attendance Approved", "Your attendance request has been approved.")
    }
    
    post(ATTENDANCE_REQUEST_REJECT)
    {
        val request = call.receive<RequestApproval>()
        
        val userProposing = dbQuery {
            val proposalDao = userProposalService.getAllProposals().single {
                it.proposer.attendooUsername == request.userProposing &&
                        request.proposedStatus == it.proposedUserStatus.name &&
                        it.currentUserStatus.name == request.currentStatus
            }
            
            userProposalService.deleteProposal(proposalDao.id.value)
            
            call.respond(HttpStatusCode.OK, mapOf(SUCCESS_JSON_FIELD_NAME to true))
            
            return@dbQuery proposalDao.proposer.email
        }
        emailService.sendEmail(userProposing, "Attendance Rejected", "Your attendance request has been rejected.")
    }
}
