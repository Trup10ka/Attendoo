package com.trup10ka.attendoo.db.dao

import com.trup10ka.attendoo.db.tables.UserAttendances
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class UserAttendance(id: EntityID<Int>) : IntEntity(id)
{
    companion object : IntEntityClass<UserAttendance>(UserAttendances)

    var userId by User referencedOn UserAttendances.userId
    var userStatusId by UserStatus referencedOn UserAttendances.userStatusId
    var startDate by UserAttendances.startDate
    var endDate by UserAttendances.endDate
}
