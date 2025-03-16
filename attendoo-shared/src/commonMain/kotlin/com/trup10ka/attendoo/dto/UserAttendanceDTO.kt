package com.trup10ka.attendoo.dto

import kotlinx.datetime.LocalDate

data class UserAttendanceDTO(
    val userId: Int,
    val userStatusId: Int,
    val startDate: LocalDate,
    val endDate: LocalDate? = null,
)
