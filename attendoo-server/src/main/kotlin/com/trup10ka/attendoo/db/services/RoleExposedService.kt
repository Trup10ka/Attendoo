package com.trup10ka.attendoo.db.services

import com.trup10ka.attendoo.db.dao.Role
import com.trup10ka.attendoo.db.tables.Roles

class RoleExposedService : RoleService
{
    override suspend fun createRole(name: String): Role
    {
        return Role.new {
            this.name = name
        }
    }
    
    override suspend fun deleteRole(name: String)
    {
        val role = getRoleByName(name)
        role?.delete()
    }
    
    override suspend fun getRoleByName(name: String): Role?
    {
        return Role.find { Roles.name eq name }.singleOrNull()
    }
    
    override suspend fun getRoleById(id: Int): Role?
    {
        return Role.findById(id)
    }
    
    override suspend fun getAllRoles(): List<Role>
    {
        return Role.all().toList()
    }
}
