package com.trup10ka.attendoo.db.client

import com.trup10ka.attendoo.db.services.*

abstract class DbClient
{
    lateinit var proposalService: ProposalService
    lateinit var roleService: RoleService
    lateinit var tagService: TagService
    lateinit var userDepartmentService: UserDepartmentService
    lateinit var userService: UserService
    lateinit var userStatusService: UserStatusService
    
    abstract fun connect()

    abstract fun init()
    
    open fun checkIfAllServicesInitialized()
    {
        if (!::proposalService.isInitialized)
        {
            throw IllegalStateException("Proposal service is not initialized")
        }
        if (!::roleService.isInitialized)
        {
            throw IllegalStateException("Role service is not initialized")
        }
        if (!::tagService.isInitialized)
        {
            throw IllegalStateException("Tag service is not initialized")
        }
        if (!::userDepartmentService.isInitialized)
        {
            throw IllegalStateException("User department service is not initialized")
        }
        if (!::userService.isInitialized)
        {
            throw IllegalStateException("User service is not initialized")
        }
        if (!::userStatusService.isInitialized)
        {
            throw IllegalStateException("User status service is not initialized")
        }
    }
}
