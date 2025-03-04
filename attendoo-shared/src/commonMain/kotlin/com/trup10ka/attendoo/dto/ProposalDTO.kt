package com.trup10ka.attendoo.dto

import kotlinx.datetime.LocalDateTime

data class ProposalDTO(
    val name: String,
    val description: String,
    val attendooProposalId: Int,
    val createdAt: LocalDateTime,
    val resolvedAt: LocalDateTime? = null,
    val currentStatus: String,
    val proposedStatus: String,
)
