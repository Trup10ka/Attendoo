package com.trup10ka.attendoo.db.services

interface EmployeeStatusService
{
    fun createEmployeeStatus(name: String)
    fun deleteEmployeeStatus(name: String)
    fun getEmployeeStatus(name: String)
    fun getEmployeeStatuses()
}
