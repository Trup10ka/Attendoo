package com.trup10ka.attendoo.db.dao

import com.trup10ka.attendoo.db.tables.EmployeeAttendances
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class EmployeeAttendance(id: EntityID<Int>) : IntEntity(id)
{
    companion object : IntEntityClass<EmployeeAttendance>(EmployeeAttendances)

    var employeeId by Employee referencedOn EmployeeAttendances.employeeId
    var employeeStatusId by EmployeeStatus referencedOn EmployeeAttendances.employeeStatusId
    var startDate by EmployeeAttendances.startDate
    var endDate by EmployeeAttendances.endDate
}
