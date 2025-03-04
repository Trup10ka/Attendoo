package com.trup10ka.attendoo.db.services

import com.trup10ka.attendoo.db.dao.UserDepartment
import com.trup10ka.attendoo.db.tables.UserDepartments

class UserDepartmentExposedService : UserDepartmentService
{
    override suspend fun createDepartment(name: String): UserDepartment
    {
        return UserDepartment.new {
            this.name = name
        }
    }
    
    override suspend fun deleteDepartmentByName(name: String)
    {
        val department = getDepartmentByName(name)
        department?.delete()
    }
    
    override suspend fun deleteDepartmentById(id: Int)
    {
        val department = getDepartmentById(id)
        department?.delete()
    }
    
    override suspend fun getDepartmentByName(name: String): UserDepartment?
    {
        return UserDepartment.find { UserDepartments.name eq name }.singleOrNull()
    }
    
    override suspend fun getDepartmentById(id: Int): UserDepartment?
    {
        return UserDepartment.findById(id)
    }
    
    override suspend fun getAllDepartments(): List<UserDepartment>
    {
        return UserDepartment.all().toList()
    }
}
