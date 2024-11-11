package com.trup10ka.attendoo.db.dao

import com.trup10ka.attendoo.db.tables.Employees
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class Employee(id: EntityID<Int>) : IntEntity(id)
{
    companion object : IntEntityClass<Employee>(Employees)

    var name by Employees.name
    var surname by Employees.surname
    var attendooUsername by Employees.attendooUsername
    var attendooPassword by Employees.attendooPassword
    var email by Employees.email
    var phone by Employees.phone
    var role by Role referencedOn Employees.role
    var defaultStatus by EmployeeStatus referencedOn Employees.defaultStatusId
    var department by EmployeeDepartment referencedOn Employees.employeeDepartment
}
