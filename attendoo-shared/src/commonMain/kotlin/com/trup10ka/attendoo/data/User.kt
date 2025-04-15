package com.trup10ka.attendoo.data

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: Int,
    val firstName: String,
    val lastName: String,
    val attendooUsername: String,
    val attendooPassword: String,
    val email: String,
    val phoneNumber: String,
    val role: String,
    val userStatus: String,
    val userDepartment: String
)
