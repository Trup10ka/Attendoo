package com.trup10ka.attendoo.db.services

class ExposedUserService  : UserService
{
    override fun createUser(name: String) {

        println("User created: $name")
    }
}
