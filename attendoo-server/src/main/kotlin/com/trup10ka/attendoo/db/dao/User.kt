package com.trup10ka.attendoo.db.dao

import com.trup10ka.attendoo.db.tables.Users
import com.trup10ka.attendoo.dto.UserDTO
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class User(id: EntityID<Int>) : IntEntity(id)
{
    companion object : IntEntityClass<User>(Users)

    var name by Users.name
    var surname by Users.surname
    var attendooUsername by Users.attendooUsername
    var attendooPassword by Users.attendooPassword
    var email by Users.email
    var phone by Users.phone
    var role by Role referencedOn Users.role
    var defaultStatus by UserStatus referencedOn Users.defaultStatusId
    var department by UserDepartment referencedOn Users.userDepartment
    
    fun toDTO(): UserDTO
    {
        return UserDTO(
            name,
            surname,
            attendooUsername,
            attendooPassword,
            email,
            phone,
            role.name,
            defaultStatus.name,
            department.name
        )
    }
}
