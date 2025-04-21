package com.trup10ka.attendoo.data

import kotlinx.serialization.Serializable

@Serializable
data class RequestApproval(
    val userProposing: String,
    val currentStatus: String,
    val proposedStatus: String
)
