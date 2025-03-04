package com.trup10ka.attendoo.db.services

interface EmployeeService
{
    suspend fun createEmployee(name: String)
    suspend fun deleteEmployee(name: String)
    suspend fun getEmployee(name: String)
    suspend fun getEmployees()
    suspend fun updateEmployee(name: String)
}
