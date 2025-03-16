package com.trup10ka.attendoo.config

data class Config(
    val port: Int,
    val host: String,
    val isSecureConnection: Boolean,
    val database: Database,
    val jwt: JWT
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
}
