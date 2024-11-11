package com.trup10ka.attendoo.db.dao

import com.trup10ka.attendoo.db.tables.EmployeeStatuses
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class EmployeeStatus(id: EntityID<Int>) : IntEntity(id)
{
    companion object : IntEntityClass<EmployeeStatus>(EmployeeStatuses)

    var name by EmployeeStatuses.name
}
