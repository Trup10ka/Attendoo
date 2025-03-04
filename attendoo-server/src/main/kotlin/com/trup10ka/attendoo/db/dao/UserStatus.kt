package com.trup10ka.attendoo.db.dao

import com.trup10ka.attendoo.db.tables.UserStatuses
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class UserStatus(id: EntityID<Int>) : IntEntity(id)
{
    companion object : IntEntityClass<UserStatus>(UserStatuses)

    var name by UserStatuses.name
}
