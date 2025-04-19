package com.trup10ka.attendoo.db.dao

import com.trup10ka.attendoo.db.tables.UserDepartmentMappings
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class UserDepartmentMapping(id: EntityID<Int>) : IntEntity(id)
{
    companion object : IntEntityClass<UserDepartmentMapping>(UserDepartmentMappings)

    var user by User referencedOn UserDepartmentMappings.user
    var department by UserDepartment referencedOn UserDepartmentMappings.department
}
