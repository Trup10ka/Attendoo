package com.trup10ka.attendoo.db.dao

import com.trup10ka.attendoo.db.tables.EmployeeDepartments
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class EmployeeDepartment(id: EntityID<Int>) : IntEntity(id)
{
    companion object : IntEntityClass<EmployeeDepartment>(EmployeeDepartments)

    var name by EmployeeDepartments.name
}
