package com.trup10ka.attendoo.security

import java.security.MessageDigest

class Sha384PasswordEncryptor : PasswordEncryptor
{
    override fun encrypt(password: String): String
    {
        val digest = MessageDigest.getInstance("SHA-384")
        val hashBytes = digest.digest(password.toByteArray())
        return hashBytes.joinToString("") { "%02x".format(it) }
    }
}
