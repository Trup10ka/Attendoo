package com.trup10ka.attendoo.db.services

import com.trup10ka.attendoo.db.dao.User
import com.trup10ka.attendoo.dto.UserDTO

interface UserService
{
    suspend fun createUser(userDTO: UserDTO): Boolean
    suspend fun deleteUserByName(name: String)
    suspend fun deleteUserById(id: Int)
    suspend fun getUserByUsername(name: String): User?
    suspend fun getUserByEmail(email: String): User?
    suspend fun getUserById(id: Int): User?
    suspend fun updateUserByUsername(userDTO: UserDTO): Boolean
    suspend fun updateUserById(id: Int, userDTO: UserDTO): Boolean
    suspend fun getAllUsersFromDepartment(department: String): List<User>
}
