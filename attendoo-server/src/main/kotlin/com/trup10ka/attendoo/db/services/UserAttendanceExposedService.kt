package com.trup10ka.attendoo.db.services

import com.trup10ka.attendoo.db.dao.UserAttendance
import com.trup10ka.attendoo.db.tables.UserAttendances
import com.trup10ka.attendoo.dto.UserAttendanceDTO
import com.trup10ka.attendoo.db.dao.UserStatus
import com.trup10ka.attendoo.db.dao.User
import com.trup10ka.attendoo.db.dbQuery
import com.trup10ka.attendoo.util.convertToJavaDate
import kotlinx.datetime.LocalDate
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.transactions.transaction

class UserAttendanceExposedService : UserAttendanceService
{
    override suspend fun createAttendance(attendanceDto: UserAttendanceDTO): Boolean
    {
        return dbQuery {
            val user = User.findById(attendanceDto.userId) ?: return@dbQuery false
            val userStatus = UserStatus.findById(attendanceDto.userStatusId) ?: return@dbQuery false
            
            UserAttendance.new {
                this.userId = user
                this.userStatusId = userStatus
                this.startDate = attendanceDto.startDate.convertToJavaDate()
                this.endDate = attendanceDto.endDate?.convertToJavaDate()
            }
            true
        }
    }
    
    override suspend fun updateAttendance(attendanceDto: UserAttendanceDTO): Boolean
    {
        val attendance = getAttendanceBySpecific(attendanceDto) ?: return false
        
        return transaction {
            
            attendance.userId = User.findById(attendanceDto.userId) ?: return@transaction false
            attendance.userStatusId = UserStatus.findById(attendanceDto.userStatusId) ?: return@transaction false
            attendance.startDate = attendanceDto.startDate.convertToJavaDate()
            attendance.endDate = attendanceDto.endDate?.convertToJavaDate()
            
            true
        }
    }
    
    override suspend fun deleteAttendance(attendanceDto: UserAttendanceDTO): Boolean
    {
        val attendance = getAttendanceBySpecific(attendanceDto) ?: return false
        
        return transaction {
            attendance.delete()
            true
        }
    }
    
    override suspend fun getAttendanceById(attendanceId: Int): UserAttendance?
    {
        return transaction {
            UserAttendance.findById(attendanceId)
        }
    }
    
    override suspend fun getAttendanceBySpecific(attendanceDto: UserAttendanceDTO): UserAttendance?
    {
        return transaction {
            UserAttendance.find {
                (UserAttendances.userId eq attendanceDto.userId) and
                (UserAttendances.userStatusId eq attendanceDto.userStatusId) and
                (UserAttendances.startDate eq attendanceDto.startDate.convertToJavaDate())
            }.firstOrNull()
        }
    }
    
    override suspend fun getAttendanceByUserId(userId: Int): List<UserAttendance>
    {
        return transaction {
            UserAttendance.find { UserAttendances.userId eq userId }.toList()
        }
    }
    
    override suspend fun getAttendanceByUserStatusId(userStatusId: Int): List<UserAttendance>
    {
        return transaction {
            UserAttendance.find { UserAttendances.userStatusId eq userStatusId }.toList()
        }
    }
    
    override suspend fun getAttendanceByDate(date: LocalDate): List<UserAttendance>
    {
        return transaction {
            UserAttendance.find { UserAttendances.startDate eq date.convertToJavaDate() }.toList()
        }
    }
    
    override suspend fun getAllAttendance(): List<UserAttendance>
    {
        return transaction {
            UserAttendance.all().toList()
        }
    }
}
