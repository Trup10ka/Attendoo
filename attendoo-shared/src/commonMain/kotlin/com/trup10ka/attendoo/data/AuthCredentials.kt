package com.trup10ka.attendoo.data

import kotlinx.serialization.Serializable

@Serializable
data class AuthCredentials(
    val username: String?,
    val password: String?
)
