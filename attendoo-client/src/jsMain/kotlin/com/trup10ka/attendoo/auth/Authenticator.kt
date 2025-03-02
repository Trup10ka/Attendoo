package com.trup10ka.attendoo.auth

interface Authenticator
{
    suspend fun login(): String
    suspend fun logout(): String
    suspend fun register(): String
    suspend fun isAuthenticated(): Boolean
    fun addAuthMethodToRequest(request: Any)
}
