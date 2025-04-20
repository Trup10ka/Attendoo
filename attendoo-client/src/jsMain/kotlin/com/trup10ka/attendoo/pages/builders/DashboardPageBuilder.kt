package com.trup10ka.attendoo.pages.builders

import com.trup10ka.attendoo.data.User
import com.trup10ka.attendoo.dto.UserAttendanceWithInfoDTO
import org.w3c.dom.HTMLElement

interface DashboardPageBuilder : PageBuilder
{
    fun buildAttendanceBox()

    fun buildDepartmentUsers(currentUser: User?, departmentUsers: List<User>)

    fun buildDynamicAttendances(attendances: List<UserAttendanceWithInfoDTO>, users: List<User>)

    override fun buildDynamicElement(appender: HTMLElement?)
    {
        throw UnsupportedOperationException("This is not supported for DashboardPageBuilder, if called, please contact the developer")
    }
}
