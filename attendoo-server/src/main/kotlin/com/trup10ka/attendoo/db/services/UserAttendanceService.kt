package com.trup10ka.attendoo.db.services

import com.trup10ka.attendoo.db.dao.UserAttendance
import com.trup10ka.attendoo.dto.UserAttendanceDTO
import kotlinx.datetime.LocalDate

interface UserAttendanceService
{
    suspend fun createAttendance(attendanceDto: UserAttendanceDTO): Boolean
    suspend fun updateAttendance(attendanceDto: UserAttendanceDTO): Boolean
    suspend fun deleteAttendance(attendanceDto: UserAttendanceDTO): Boolean
    suspend fun getAttendanceById(attendanceId: Int): UserAttendance?
    suspend fun getAttendanceBySpecific(attendanceDto: UserAttendanceDTO): UserAttendance?
    suspend fun getAttendanceByUserId(userId: Int): List<UserAttendance>
    suspend fun getAttendanceByUserStatusId(userStatusId: Int): List<UserAttendance>
    suspend fun getAttendanceByDate(date: LocalDate): List<UserAttendance>
    suspend fun getAllAttendancesInDepartment(departmentId: Int): List<UserAttendance>
    suspend fun getAllAttendance(): List<UserAttendance>
}
