package com.trup10ka.attendoo.db.tables

import org.jetbrains.exposed.dao.id.IntIdTable

object EmployeeStatuses : IntIdTable("employee_status")
{
    val name = varchar("name", 50)
}
