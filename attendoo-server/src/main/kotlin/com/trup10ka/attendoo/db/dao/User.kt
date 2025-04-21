package com.trup10ka.attendoo.db.dao

import com.trup10ka.attendoo.db.tables.UserDepartmentMappings
import com.trup10ka.attendoo.db.tables.Users
import com.trup10ka.attendoo.dto.UserDTO
import com.trup10ka.attendoo.dto.UserDTOWithID
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.SizedIterable

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
    
    val departments by UserDepartmentMapping referrersOn UserDepartmentMappings.user
    
    fun isAdmin(): Boolean
    {
        return role.name.equals("admin", ignoreCase = true)
    }
    
    fun getAllDepartments(): List<UserDepartment>
    {
        return if (isAdmin())
        {
            departments.map { it.department }
        }
        else
        {
            listOf(department)
        }
    }
    
    fun toDTO(): UserDTO
    {
        val departmentNames = if (isAdmin())
        {
            getAllDepartments().map { it.name }
        }
        else
        {
            null
        }
        
        return UserDTO(
            firstName = name,
            lastName = surname,
            attendooUsername = attendooUsername,
            attendooPassword = attendooPassword,
            email = email,
            phoneNumber = phone,
            role = role.name,
            userStatus = defaultStatus.name,
            userDepartment = department.name,
            userDepartments = departmentNames
        )
    }
    
    fun toDTOWithID(): UserDTOWithID
    {
        return UserDTOWithID(
            id.value,
            firstName = name,
            lastName = surname,
            attendooUsername = attendooUsername,
            attendooPassword = attendooPassword,
            email = email,
            phoneNumber = phone,
            role = role.name,
            userStatus = defaultStatus.name,
            userDepartment = department.name
        )
    }
}
