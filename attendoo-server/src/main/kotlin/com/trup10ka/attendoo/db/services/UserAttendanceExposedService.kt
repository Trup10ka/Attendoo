package com.trup10ka.attendoo.db.services

import com.trup10ka.attendoo.db.dao.UserAttendance
import com.trup10ka.attendoo.db.tables.UserAttendances
import com.trup10ka.attendoo.dto.UserAttendanceDTO
import com.trup10ka.attendoo.util.convertToJavaDate
import kotlinx.datetime.LocalDate
import org.jetbrains.exposed.sql.and

class UserAttendanceExposedService(
    private val userStatusService: UserStatusService,
    private val userDepartmentService: UserDepartmentService,
    private val userService: UserService
) : UserAttendanceService
{
    override suspend fun createAttendance(attendanceDto: UserAttendanceDTO): Boolean
    {
        val user = userService.getUserById(attendanceDto.userId) ?: return false
        val userStatus = userStatusService.getUserStatusById(attendanceDto.userStatusId) ?: return false
        
        UserAttendance.new {
            this.userId = user
            this.userStatusId = userStatus
            this.startDate = attendanceDto.startDate.convertToJavaDate()
            this.endDate = attendanceDto.endDate?.convertToJavaDate()
        }
        return true
    }
    
    override suspend fun updateAttendance(attendanceDto: UserAttendanceDTO): Boolean
    {
        val attendance = getAttendanceBySpecific(attendanceDto) ?: return false
        
        attendance.userId = userService.getUserById(attendanceDto.userId) ?: return false
        attendance.userStatusId = userStatusService.getUserStatusById(attendanceDto.userStatusId) ?: return false
        attendance.startDate = attendanceDto.startDate.convertToJavaDate()
        attendance.endDate = attendanceDto.endDate?.convertToJavaDate()
        
        return true
    }
    
    override suspend fun deleteAttendance(attendanceDto: UserAttendanceDTO): Boolean
    {
        val attendance = getAttendanceBySpecific(attendanceDto) ?: return false
        attendance.delete()
        return true
    }
    
    override suspend fun getAttendanceById(attendanceId: Int): UserAttendance?
    {
        return UserAttendance.findById(attendanceId)
    }
    
    override suspend fun getAttendanceBySpecific(attendanceDto: UserAttendanceDTO): UserAttendance?
    {
        return UserAttendance.find {
            (UserAttendances.userId eq attendanceDto.userId) and
                    (UserAttendances.userStatusId eq attendanceDto.userStatusId) and
                    (UserAttendances.startDate eq attendanceDto.startDate.convertToJavaDate())
        }.firstOrNull()
    }
    
    override suspend fun getAttendanceByUserId(userId: Int): List<UserAttendance>
    {
        return UserAttendance.find { UserAttendances.userId eq userId }.toList()
    }
    
    override suspend fun getAttendanceByUserStatusId(userStatusId: Int): List<UserAttendance>
    {
        return UserAttendance.find { UserAttendances.userStatusId eq userStatusId }.toList()
    }
    
    override suspend fun getAttendanceByDate(date: LocalDate): List<UserAttendance>
    {
        return UserAttendance.find { UserAttendances.startDate eq date.convertToJavaDate() }.toList()
    }
    
    override suspend fun getAllAttendancesInDepartment(departmentId: Int): List<UserAttendance>
    {
        val department = userDepartmentService.getDepartmentById(departmentId) ?: return emptyList()
        val usersInDepartment = userService.getAllUsersFromDepartment(department.name)
        return UserAttendance.find { UserAttendances.userId inList usersInDepartment.map { it.id } }.toList()
    }
    
    override suspend fun getAllAttendance(): List<UserAttendance>
    {
        return UserAttendance.all().toList()
    }
}
