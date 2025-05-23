package com.trup10ka.attendoo.api.attendances

import com.trup10ka.attendoo.ATTENDANCES_ENDPOINT
import com.trup10ka.attendoo.db.client.DbClient
import com.trup10ka.attendoo.db.services.ProposalService
import com.trup10ka.attendoo.db.services.UserAttendanceService
import com.trup10ka.attendoo.db.services.UserDepartmentService
import com.trup10ka.attendoo.db.services.UserService
import com.trup10ka.attendoo.mail.EmailService
import io.ktor.server.routing.Route
import io.ktor.server.routing.route

fun Route.routeAttendances(
    userAttendanceService: UserAttendanceService, 
    userService: UserService,
    userDepartmentService: UserDepartmentService,
    userProposalService: ProposalService,
    emailService: EmailService
)
{
    route(ATTENDANCES_ENDPOINT) {
        routeGetAllAttendances(userAttendanceService, userService, userDepartmentService)
        routeGetUserAttendances(userAttendanceService, userService)
        routesProcessRequestApprove(userAttendanceService, userProposalService, emailService)
    }
}
