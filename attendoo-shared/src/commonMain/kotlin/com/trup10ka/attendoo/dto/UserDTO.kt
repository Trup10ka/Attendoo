package com.trup10ka.attendoo.dto

import kotlinx.serialization.Serializable


@Serializable
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
    
    fun convertToDTOWithEncryptedPassword(encryptedPassword: String): UserDTO
    {
        return UserDTO(
            this.firstName,
            this.lastName,
            this.attendooUsername,
            encryptedPassword,
            this.email,
            this.phoneNumber,
            this.role,
            this.userStatus,
            this.userDepartment
        )
    }
}
