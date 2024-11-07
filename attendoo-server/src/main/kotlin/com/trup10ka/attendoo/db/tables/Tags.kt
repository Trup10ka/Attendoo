package com.trup10ka.attendoo.db.tables

import org.jetbrains.exposed.dao.id.IntIdTable

object Tags : IntIdTable("tag")
{
    val name = varchar("tag_name", 50)
}
