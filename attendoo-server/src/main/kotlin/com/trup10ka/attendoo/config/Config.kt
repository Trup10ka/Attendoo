package com.trup10ka.attendoo.config

data class Config(
    val port: Int,
    val host: String,
    val isSecureConnection: Boolean,
    val database: Database,
    val jwt: JWT,
    val email: Email
)
{
    data class Database(
        val url: String,
        val port: Int,
        val name: String,
        val user: String,
        val password: String,
        val driver: String
    )

    data class JWT(
        val secret: String,
        val issuer: String,
        val audience: String,
        val realm: String
    )

    data class Email(
        val host: String,
        val port: Int,
        val username: String,
        val password: String,
        val fromEmail: String,
        val fromName: String
    )
}
