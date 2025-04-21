package com.trup10ka.attendoo.dto

import kotlinx.datetime.LocalDate
import kotlinx.serialization.Serializable

@Serializable
data class RequestDTONoProposer(
    val proposed: String,
    val proposedStatus: String,
    val endDate: LocalDate,
)
