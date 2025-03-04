package com.trup10ka.attendoo.dto

data class UserDTO(
    val firstName: String? = null,
    val lastName: String? = null,
    val attendooUsername: String? = null,
    val attendooPassword: String? = null,
    val email: String? = null,
    val phoneNumber: String? = null,
    val role: String? = null,
    val userStatus: String? = null,
    val userDepartment: String? = null
)
{
    fun checkIfValidForUserCreation(): Boolean
    {
        return firstName != null &&
                lastName != null &&
                attendooUsername != null &&
                attendooPassword != null &&
                email != null &&
                phoneNumber != null &&
                role != null &&
                userStatus != null &&
                userDepartment != null
    }
}
