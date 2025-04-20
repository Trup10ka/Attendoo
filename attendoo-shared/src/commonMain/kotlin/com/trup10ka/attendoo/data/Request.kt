package com.trup10ka.attendoo.data

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class Request(
    val proposer: User,
    val proposed: User,
    val proposedDepartment: String,
    val note: String,
    val currentStatus: String,
    val proposedStatus: String,
    val resolvedAt: LocalDateTime? = null
)
