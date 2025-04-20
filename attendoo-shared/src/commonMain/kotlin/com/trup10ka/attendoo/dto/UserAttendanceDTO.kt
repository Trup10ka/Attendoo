package com.trup10ka.attendoo.dto

import kotlinx.datetime.LocalDate
import kotlinx.serialization.Serializable

@Serializable
data class UserAttendanceDTO(
    val userId: Int,
    val userStatusId: Int,
    val startDate: LocalDate,
    val endDate: LocalDate? = null,
)
