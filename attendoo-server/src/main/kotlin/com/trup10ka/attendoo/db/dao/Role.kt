package com.trup10ka.attendoo.db.dao

import com.trup10ka.attendoo.db.tables.Roles
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class Role(id: EntityID<Int>) : IntEntity(id)
{
    companion object : IntEntityClass<Role>(Roles)

    var name by Roles.name
}
