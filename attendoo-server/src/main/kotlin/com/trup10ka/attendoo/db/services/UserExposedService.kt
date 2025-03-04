package com.trup10ka.attendoo.db.services

import com.trup10ka.attendoo.db.dao.User
import com.trup10ka.attendoo.db.tables.Users
import com.trup10ka.attendoo.dto.UserDTO
import com.trup10ka.attendoo.plugins.routing.logger
import kotlinx.coroutines.runBlocking

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
        
        val defaultStatusDao = userStatusService.getUserStatusByName(userDTO.userStatus!!)
            ?: userStatusService.createUserStatus(userDTO.userStatus!!)
        
        val departmentDao = userDepartmentService.getDepartmentByName(userDTO.userDepartment!!)
            ?: userDepartmentService.createDepartment(userDTO.userDepartment!!)
        
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
        return User.find { Users.attendooUsername eq username }.singleOrNull()
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
        User.find { Users.attendooUsername eq userDTO.attendooUsername!! }.singleOrNull()?.apply(applyUpdateChanges(userDTO))
            ?: run {
                logger.warn { "User with username ${userDTO.attendooUsername} not found" }
                return false
            }
        
        return true
    }
    
    override suspend fun updateUserById(id: Int, userDTO: UserDTO): Boolean
    {
        User.findByIdAndUpdate(id, applyUpdateChanges(userDTO))
            ?: run {
                logger.warn { "User with id $id not found" }
                return false
            }
        return true
    }
    
    private fun applyUpdateChanges(userDTO: UserDTO): (User) -> Unit = { userDao ->
        userDTO.firstName?.let { userDao.name = it }
        userDTO.lastName?.let { userDao.surname = it }
        userDTO.attendooUsername?.let { userDao.attendooUsername = it }
        userDTO.attendooPassword?.let { userDao.attendooPassword = it }
        userDTO.email?.let { userDao.email = it }
        userDTO.phoneNumber?.let { userDao.phone = it }
        userDTO.role?.let { userDao.role = runBlocking { roleService.getRoleByName(it)!! } }
        userDTO.userStatus?.let { userDao.defaultStatus = runBlocking { userStatusService.getUserStatusByName(it)!! } }
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
