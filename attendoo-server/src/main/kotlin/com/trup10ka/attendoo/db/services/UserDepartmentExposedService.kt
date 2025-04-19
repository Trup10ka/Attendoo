package com.trup10ka.attendoo.db.services

import com.trup10ka.attendoo.db.dao.User
import com.trup10ka.attendoo.db.dao.UserDepartment
import com.trup10ka.attendoo.db.dao.UserDepartmentMapping
import com.trup10ka.attendoo.db.tables.UserDepartmentMappings
import com.trup10ka.attendoo.db.tables.UserDepartments
import org.jetbrains.exposed.sql.and

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
    
    override suspend fun assignDepartmentToUser(userId: Int, departmentId: Int): UserDepartmentMapping?
    {
        val user = User.findById(userId) ?: return null
        val department = UserDepartment.findById(departmentId) ?: return null
        
        if (!user.isAdmin())
        {
            return null
        }
        
        val existingMapping = UserDepartmentMapping.find {
            (UserDepartmentMappings.user eq userId) and (UserDepartmentMappings.department eq departmentId)
        }.singleOrNull()
        
        if (existingMapping != null)
        {
            return existingMapping
        }
        
        return UserDepartmentMapping.new {
            this.user = user
            this.department = department
        }
    }
    
    override suspend fun removeDepartmentFromUser(userId: Int, departmentId: Int)
    {
        val mapping = UserDepartmentMapping.find {
            (UserDepartmentMappings.user eq userId) and (UserDepartmentMappings.department eq departmentId)
        }.singleOrNull()
        
        mapping?.delete()
    }
    
    override suspend fun getUserDepartments(userId: Int): List<UserDepartment>
    {
        val user = User.findById(userId) ?: return emptyList()
        return user.getAllDepartments()
    }
}
