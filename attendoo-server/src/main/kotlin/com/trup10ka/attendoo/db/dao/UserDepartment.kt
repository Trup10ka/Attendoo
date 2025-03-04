package com.trup10ka.attendoo.db.dao

import com.trup10ka.attendoo.db.tables.UserDepartments
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class UserDepartment(id: EntityID<Int>) : IntEntity(id)
{
    companion object : IntEntityClass<UserDepartment>(UserDepartments)

    var name by UserDepartments.name
}
