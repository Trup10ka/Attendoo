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
    val userDepartment: String? = null,
    val userDepartments: List<String>? = null
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
            this.userDepartment,
            this.userDepartments
        )
    }
    
    fun isAdmin(): Boolean
    {
        return role?.equals("admin", ignoreCase = true) ?: false
    }
    
    fun getAllDepartments(): List<String>
    {
        return if (isAdmin() && userDepartments != null)
        {
            userDepartments
        }
        else if (userDepartment != null)
        {
            listOf(userDepartment)
        }
        else
        {
            emptyList()
        }
    }
}
