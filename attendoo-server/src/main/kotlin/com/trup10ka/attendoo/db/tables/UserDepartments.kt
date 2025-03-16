package com.trup10ka.attendoo.db.tables

import org.jetbrains.exposed.dao.id.IntIdTable

object UserDepartments : IntIdTable("user_department")
{
    val name = varchar("name", 50)
}
