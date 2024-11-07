package com.trup10ka.attendoo.db.tables

import org.jetbrains.exposed.dao.id.IntIdTable

object Roles : IntIdTable("role")
{
    val name = varchar("name", 50)
}
