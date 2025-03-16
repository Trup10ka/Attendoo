package com.trup10ka.attendoo.db.tables

import org.jetbrains.exposed.dao.id.IntIdTable

object UserStatuses : IntIdTable("user_status")
{
    val name = varchar("name", 50)
}
