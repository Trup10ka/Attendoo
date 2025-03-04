package com.trup10ka.attendoo.db.tables

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.javatime.date

object UserAttendances : IntIdTable("employee_attendances")
{
    val employeeId = reference("employee_id", Users)
    val employeeStatusId = reference("employee_status_id", UserStatuses)
    val startDate = date("start_date")
    val endDate = date("end_date")
}
