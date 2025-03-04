package com.trup10ka.attendoo.db.services

import com.trup10ka.attendoo.db.dao.UserStatus
import com.trup10ka.attendoo.db.tables.UserStatuses

class UserStatusExposedService : UserStatusService
{
    override suspend fun createUserStatus(name: String): UserStatus
    {
        return UserStatus.new {
            this.name = name
        }
    }
    
    override suspend fun deleteUserStatus(name: String)
    {
        val status = getUserStatusByName(name)
        status?.delete()
    }
    
    override suspend fun getUserStatusByName(name: String): UserStatus?
    {
        return UserStatus.find { UserStatuses.name eq name }.singleOrNull()
    }
    
    override suspend fun getUserStatusById(id: Int): UserStatus?
    {
        return UserStatus.findById(id)
    }
    
    override suspend fun getAllUserStatuses(): List<UserStatus>
    {
        return UserStatus.all().toList()
    }
    
}
