package com.trup10ka.attendoo.db.services

import com.trup10ka.attendoo.db.dao.UserDepartment
import com.trup10ka.attendoo.db.dao.UserStatus
import com.trup10ka.attendoo.db.dao.User
import com.trup10ka.attendoo.db.tables.UserDepartments
import com.trup10ka.attendoo.db.tables.UserStatuses
import com.trup10ka.attendoo.db.tables.Users
import com.trup10ka.attendoo.dto.UserDTO
import com.trup10ka.attendoo.plugins.routing.logger

class UserExposedService(
    private val roleService: RoleService,
    private val userStatusService: UserStatusService,
    private val userDepartmentService: UserDepartmentService
) : UserService
{
    override suspend fun createUser(userDTO: UserDTO)
    {
        checkIfCanInsertNewUser(userDTO)
        
        val roleDao = roleService.getRoleByName(userDTO.role!!) ?: roleService.createRole(userDTO.role!!)
        
        val defaultStatusDao =
            UserStatus.find { UserStatuses.name eq userDTO.userStatus!! }.firstOrNull() ?: UserStatus.new {
                name = userDTO.userStatus!!
            }
        
        val departmentDao = UserDepartment.find { UserDepartments.name eq userDTO.userDepartment!! }.firstOrNull()
            ?: UserDepartment.new {
                name = userDTO.userDepartment!!
            }
        
        User.new {
            name = userDTO.firstName!!
            surname = userDTO.lastName!!
            attendooUsername = userDTO.attendooUsername!!
            attendooPassword = userDTO.attendooPassword!!
            email = userDTO.email!!
            phone = userDTO.phoneNumber!!
            role = roleDao
            defaultStatus = defaultStatusDao
            department = departmentDao
        }
        
    }
    
    override suspend fun deleteUserByName(name: String)
    {
        User.find { Users.name eq name }.forEach { it.delete() }
    }
    
    override suspend fun deleteUserById(id: Int)
    {
        User.findById(id)?.delete()
    }
    
    override suspend fun getUserByUsername(username: String): User?
    {
        val result = User.find { Users.attendooUsername eq username }.singleOrNull()
    }
    
    override suspend fun getUserByEmail(email: String): User?
    {
        return User.find { Users.email eq email }.singleOrNull()
    }
    
    override suspend fun getUserById(id: Int): User?
    {
        return User.findById(id)
    }
    
    override suspend fun updateUserByUsername(userDTO: UserDTO): Boolean
    {
    
    }
    
    override suspend fun updateUserById(id: Int, userDTO: UserDTO): Boolean
    {
        TODO("Not yet implemented")
    }
    
    
    private suspend fun checkIfCanInsertNewUser(userDTO: UserDTO): Boolean
    {
        if (!userDTO.checkIfValidForUserCreation())
        {
            logger.warn { "UserDTO is not valid for user creation: $userDTO" }
            return false
        }
        
        if (getUserByUsername(userDTO.attendooUsername!!) != null)
        {
            logger.warn { "User with email ${userDTO.attendooUsername} already exists" }
            return false
        }
        return true
    }
}
