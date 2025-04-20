package com.trup10ka.attendoo.plugins

import com.trup10ka.attendoo.db.client.DbClient

fun configureDB(dbClient: DbClient)
{
    dbClient.connect()
    dbClient.init()
}
