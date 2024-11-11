package com.trup10ka.attendoo.db.dao

import com.trup10ka.attendoo.db.tables.Tags
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class Tag(id: EntityID<Int>) : IntEntity(id)
{
    companion object : IntEntityClass<Tag>(Tags)

    var name by Tags.name
}
