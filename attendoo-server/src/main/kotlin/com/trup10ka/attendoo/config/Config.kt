package com.trup10ka.attendoo.config

data class Config(
    val port: Int,
    val host: String,
    val database: Database,
    val isSecureConnection: Boolean
)
{
    data class Database(
        val url: String,
        val name: String,
        val user: String,
        val password: String,
        val driver: String
    )
}
