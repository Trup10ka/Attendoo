package com.trup10ka.attendoo.config

data class Config(
    val port: Int,
    val host: String,
    val database: Database
)
{
    data class Database(
        val host: String,
        val port: Int,
        val name: String,
        val user: String,
        val password: String,
    )
}
