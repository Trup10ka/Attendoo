package com.trup10ka.attendoo.db.services

import com.trup10ka.attendoo.db.dao.User
import com.trup10ka.attendoo.db.dao.UserDepartment
import com.trup10ka.attendoo.db.dao.UserDepartmentMapping

interface UserDepartmentService
{
    suspend fun createDepartment(name: String): UserDepartment
    suspend fun deleteDepartmentByName(name: String)
    suspend fun deleteDepartmentById(id: Int)
    suspend fun getDepartmentByName(name: String): UserDepartment?
    suspend fun getDepartmentById(id: Int): UserDepartment?
    suspend fun getAllDepartments(): List<UserDepartment>
    
    suspend fun assignDepartmentToUser(userId: Int, departmentId: Int): UserDepartmentMapping?
    suspend fun removeDepartmentFromUser(userId: Int, departmentId: Int)
    suspend fun getUserDepartments(userId: Int): List<UserDepartment>
}
