package com.trup10ka.attendoo.security

interface PasswordEncryptor
{
    fun encrypt(password: String): String
}
