package com.trup10ka.attendoo.db.services

interface EmployeeService
{
    fun createEmployee(name: String)
    fun deleteEmployee(name: String)
    fun getEmployee(name: String)
    fun getEmployees()
    fun updateEmployee(name: String)
}
