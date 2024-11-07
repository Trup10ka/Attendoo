package com.trup10ka.attendoo.db.tables

import org.jetbrains.exposed.dao.id.IntIdTable

object Employees : IntIdTable("employee")
{
    val name = varchar("first_name", 255)
    val surname = varchar("last_name", 255)
    val attendooUsername = varchar("attendoo_username", 255)
    val attendooPassword = varchar("attendoo_password", 255)
    val email = varchar("email", 255)
    val phone = varchar("phone_number", 255)
    val role = reference("role_id", Roles)
    val defaultStatusId = reference("default_employee_status_id", EmployeeStatuses)
    val employeeDepartment = reference("employee_department_id", EmployeeDepartments)
}
