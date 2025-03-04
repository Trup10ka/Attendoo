package com.trup10ka.attendoo.db.tables

import org.jetbrains.exposed.dao.id.IntIdTable

object EmployeeDepartments : IntIdTable("employee_department")
{
    val name = varchar("name", 50)
}
