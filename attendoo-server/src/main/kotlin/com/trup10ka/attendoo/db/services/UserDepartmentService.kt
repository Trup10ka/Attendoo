package com.trup10ka.attendoo.db.services

import com.trup10ka.attendoo.db.dao.UserDepartment

interface UserDepartmentService
{
    suspend fun createDepartment(name: String): UserDepartment
    suspend fun deleteDepartmentByName(name: String)
    suspend fun deleteDepartmentById(id: Int)
    suspend fun getDepartmentByName(name: String): UserDepartment?
    suspend fun getDepartmentById(id: Int): UserDepartment?
    suspend fun getAllDepartments(): List<UserDepartment>
}
