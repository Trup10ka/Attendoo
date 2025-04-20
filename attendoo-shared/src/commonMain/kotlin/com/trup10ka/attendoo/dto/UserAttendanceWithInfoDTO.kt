package com.trup10ka.attendoo.dto

import kotlinx.datetime.LocalDate
import kotlinx.serialization.Serializable

@Serializable
data class UserAttendanceWithInfoDTO(
    // User information
    val userId: Int,
    val firstName: String,
    val lastName: String,
    val attendooUsername: String,
    val userDepartment: String,
    
    // Status information
    val userStatus: String,
    
    // Attendance information
    val startDate: LocalDate,
    val endDate: LocalDate? = null,
)
