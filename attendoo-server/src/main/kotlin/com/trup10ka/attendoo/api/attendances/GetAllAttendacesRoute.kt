package com.trup10ka.attendoo.api.attendances

import com.trup10ka.attendoo.ALL_ATTENDANCE_ENDPOINT
import com.trup10ka.attendoo.ATTENDANCES_ENDPOINT
import com.trup10ka.attendoo.ERROR_JSON_FIELD_NAME
import com.trup10ka.attendoo.api.attendooDepartment
import com.trup10ka.attendoo.db.client.DbClient
import com.trup10ka.attendoo.db.dbQuery
import com.trup10ka.attendoo.db.services.UserAttendanceService
import com.trup10ka.attendoo.db.services.UserDepartmentService
import com.trup10ka.attendoo.db.services.UserService
import com.trup10ka.attendoo.dto.UserAttendanceWithInfoDTO
import com.trup10ka.attendoo.util.convertToKotlinxDate
import io.ktor.http.HttpStatusCode
import io.ktor.server.auth.jwt.JWTPrincipal
import io.ktor.server.auth.principal
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get

fun Route.routeGetAllAttendances(
    userAttendanceService: UserAttendanceService,
    userService: UserService,
    userDepartmentService: UserDepartmentService
)
{
    get(ALL_ATTENDANCE_ENDPOINT)
    {
        // Get the principal from the JWT token
        val principal = call.principal<JWTPrincipal>()
        if (principal == null)
        {
            call.respond(HttpStatusCode.Unauthorized, mapOf(ERROR_JSON_FIELD_NAME to "No JWT token provided"))
            return@get
        }

        // Get the department from the JWT token
        val department = principal.attendooDepartment

        val attendances = dbQuery {
            // Get the department ID from the department name
            val departmentObj = userDepartmentService.getDepartmentByName(department)
            if (departmentObj == null)
            {
                // If department not found, return empty list
                emptyList()
            }
            else
            {
                // Get attendances for the department
                userAttendanceService.getAllAttendancesInDepartment(departmentObj.id.value)
            }
        }

        // Map UserAttendance DAOs to UserAttendanceWithInfoDTO objects
        val attendanceDTOs = dbQuery {
            attendances.map { attendance ->
                val user = attendance.userId
                val status = attendance.userStatusId

                UserAttendanceWithInfoDTO(
                    userId = user.id.value,
                    firstName = user.name,
                    lastName = user.surname,
                    attendooUsername = user.attendooUsername,
                    userDepartment = user.department.name,
                    userStatus = status.name,
                    startDate = attendance.startDate.convertToKotlinxDate(),
                    endDate = attendance.endDate?.convertToKotlinxDate()
                )
            }
        }

        call.respond(HttpStatusCode.OK, attendanceDTOs)
    }
}
