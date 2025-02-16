package com.trup10ka.attendoo.auth

interface Authenticator
{
    fun login()
    fun logout()
    fun register()
    fun addAuthMethodToRequest(request: Any)
}
