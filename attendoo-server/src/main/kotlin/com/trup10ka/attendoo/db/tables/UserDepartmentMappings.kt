package com.trup10ka.attendoo.db.tables

import org.jetbrains.exposed.dao.id.IntIdTable

object UserDepartmentMappings : IntIdTable("user_department_mapping")
{
    val user = reference("user_id", Users)
    val department = reference("user_department_id", UserDepartments)
}
