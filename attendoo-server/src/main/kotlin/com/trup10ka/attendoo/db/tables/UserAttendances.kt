package com.trup10ka.attendoo.db.tables

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.javatime.date

object UserAttendances : IntIdTable("user_attendances")
{
    val userId = reference("user_id", Users)
    val userStatusId = reference("user_status_id", UserStatuses)
    val startDate = date("start_date")
    val endDate = date("end_date").nullable()
}
