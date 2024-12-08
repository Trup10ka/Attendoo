package com.trup10ka.attendoo.db.services

interface RoleService
{
    fun createRole(name: String)
    fun deleteRole(name: String)
    fun getRole(name: String)
    fun getRoles()
}
