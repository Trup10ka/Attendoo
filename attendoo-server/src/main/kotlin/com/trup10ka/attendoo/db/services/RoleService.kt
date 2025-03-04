package com.trup10ka.attendoo.db.services

import com.trup10ka.attendoo.db.dao.Role


interface RoleService
{
    suspend fun createRole(name: String): Role
    suspend fun deleteRole(name: String)
    suspend fun getRoleByName(name: String): Role?
    suspend fun getRoleById(id: Int): Role?
    suspend fun getAllRoles(): List<Role>
}
