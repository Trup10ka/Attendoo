package com.trup10ka.attendoo.data

import kotlinx.datetime.LocalDateTime

data class Proposal(
    val id: Int,
    val name: String,
    val attendooCustomProposalId: Int,
    val description: String?,
    val createdAt: LocalDateTime,
    val resolvedAt: LocalDateTime?
)
