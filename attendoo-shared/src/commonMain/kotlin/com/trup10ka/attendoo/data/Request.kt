package com.trup10ka.attendoo.data

import kotlinx.serialization.Serializable

@Serializable
data class Request(
    val user: User,
    val company: String,
    val note: String,
    val status: String
)
