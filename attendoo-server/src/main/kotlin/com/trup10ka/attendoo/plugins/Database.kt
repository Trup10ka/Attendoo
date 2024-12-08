package com.trup10ka.attendoo.plugins

import com.trup10ka.attendoo.db.client.DbClient
import io.ktor.server.application.Application

fun Application.configureDB(dbClient: DbClient)
{
    dbClient.connect()
    dbClient.init()
}
