package com.trup10ka.attendoo.data

import kotlinx.datetime.LocalDateTime

data class Proposal(
    val id: Int,
    val name: String,
    val proposerId: Int,
    val proposedId: Int,
    val description: String?,
    val createdAt: LocalDateTime,
    val resolvedAt: LocalDateTime?
)
