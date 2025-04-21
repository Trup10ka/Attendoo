package com.trup10ka.attendoo.plugins.routing

import com.trup10ka.attendoo.api.attendances.routeAttendances
import com.trup10ka.attendoo.api.auth.routeAuth
import com.trup10ka.attendoo.api.department.routeDepartments
import com.trup10ka.attendoo.api.requests.routeRequests
import com.trup10ka.attendoo.api.roles.routeRoles
import com.trup10ka.attendoo.api.statuses.routeStatuses
import com.trup10ka.attendoo.api.users.routeUsers
import com.trup10ka.attendoo.db.client.DbClient
import com.trup10ka.attendoo.mail.EmailService
import io.ktor.server.auth.authenticate
import io.ktor.server.routing.Route

fun Route.routeAPICalls(dbClient: DbClient, emailService: EmailService)
{
    routeAuth(dbClient, emailService)
    
    authenticate {
        routeUsers(dbClient.userService)
        routeAttendances(dbClient.attendanceService, dbClient.userService, dbClient.userDepartmentService)
        routeRequests(dbClient, emailService)
        routeRoles(dbClient)
        routeDepartments(dbClient.userDepartmentService)
        routeStatuses(dbClient.userStatusService)
    }
}
