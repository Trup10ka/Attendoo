package com.trup10ka.attendoo.auth

interface Authenticator
{
    suspend fun login(username: String, password: String): Boolean
    suspend fun logout(): String
    suspend fun register(): String
    suspend fun isAuthenticated(): Boolean
    fun addAuthMethodToRequest(request: Any)
}
