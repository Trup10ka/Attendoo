package com.trup10ka.attendoo.db.services

import com.trup10ka.attendoo.db.dao.UserStatus

interface UserStatusService
{
    suspend fun createUserStatus(name: String): UserStatus
    suspend fun deleteUserStatus(name: String)
    suspend fun getUserStatusByName(name: String): UserStatus?
    suspend fun getUserStatusById(id: Int): UserStatus?
    suspend fun getAllUserStatuses(): List<UserStatus>
}
