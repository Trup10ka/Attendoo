package com.trup10ka.attendoo.pages.builders

import com.trup10ka.attendoo.data.User
import com.trup10ka.attendoo.dto.UserAttendanceWithInfoDTO
import com.trup10ka.attendoo.util.createDiv
import com.trup10ka.attendoo.util.createHeader
import com.trup10ka.attendoo.util.stylesOf
import org.w3c.dom.HTMLDivElement
import org.w3c.dom.HTMLElement
import com.trup10ka.attendoo.pages.constant.StyleClass.*
import com.trup10ka.attendoo.pages.constant.ElementID
import kotlinx.browser.document

class DashboardPageBuilderImp() : DashboardPageBuilder
{
    override val currentlyActiveHTMLElements = mutableSetOf<HTMLElement>()

    override fun buildAttendanceBox()
    {
        // Find the main element to append to
        val mainElement = document.getElementById(ElementID.MAIN.toString()) as HTMLElement

        // Create the dashboard container
        val dashboardContainer = createDiv(
            id = ElementID.DASHBOARD_CONTAINER.toString(),
            clazz = stylesOf(INNER_CONTAINER, DASHBOARD_CONTAINER)
        )

        // Add the dashboard container to the main element
        mainElement.appendChild(dashboardContainer)
        currentlyActiveHTMLElements.add(dashboardContainer)
    }

    override fun buildDepartmentUsers(currentUser: User?, departmentUsers: List<User>)
    {
        val dashboardContainer =
            document.getElementById(ElementID.DASHBOARD_CONTAINER.toString()) as HTMLElement? ?: return

        val leftSection = createLeftSection(departmentUsers)
        dashboardContainer.appendChild(leftSection)
        currentlyActiveHTMLElements.add(leftSection)
    }

    override fun buildDynamicAttendances(attendances: List<UserAttendanceWithInfoDTO>, users: List<User>)
    {
        val dashboardContainer =
            document.getElementById(ElementID.DASHBOARD_CONTAINER.toString()) as HTMLElement? ?: return

        val rightSection = createRightSection(attendances)
        dashboardContainer.appendChild(rightSection)
        currentlyActiveHTMLElements.add(rightSection)
    }

    private fun createLeftSection(departmentUsers: List<User>): HTMLDivElement
    {
        // Create header for the left section
        val header = createHeader(
            id = null,
            clazz = stylesOf(ORANGE, HEADER_MARGIN),
            text = "Default statuses",
            level = 1
        )
        header.id = ElementID.YOUR_STATUS_HEADER.toString()

        // If no users are available, show a message
        if (departmentUsers.isEmpty())
        {
            return createDiv(
                id = ElementID.LEFT_SECTION.toString(),
                clazz = stylesOf(SECTION, LEFT),
                children = arrayOf(
                    header,
                    createDiv(
                        id = ElementID.NO_USER_DATA.toString(),
                        clazz = stylesOf(CONTAINER_TAB, USER),
                        text = "No user data available"
                    )
                )
            )
        }

        // Create user containers for department users
        val userContainers = departmentUsers.map { user ->
            createUserContainer(user)
        }.toTypedArray()

        // Create users container
        val usersContainer = createDiv(
            id = ElementID.USERS_CONTAINER.toString(),
            clazz = stylesOf(USERS_CONTAINER),
            children = userContainers
        )

        return createDiv(
            id = ElementID.LEFT_SECTION.toString(),
            clazz = stylesOf(SECTION, LEFT),
            children = arrayOf(header, usersContainer)
        )
    }

    private fun createRightSection(attendances: List<UserAttendanceWithInfoDTO>): HTMLDivElement
    {
        // Create header for the right section
        val header = createHeader(
            id = null,
            clazz = stylesOf(ORANGE, HEADER_MARGIN),
            text = "Dynamic attendances",
            level = 1
        )
        header.id = ElementID.AVAILABLE_USERS_HEADER.toString()

        // If no attendances are available, show a message
        if (attendances.isEmpty())
        {
            return createDiv(
                id = ElementID.RIGHT_SECTION.toString(),
                clazz = stylesOf(SECTION, RIGHT),
                children = arrayOf(
                    header,
                    createDiv(
                        id = "no-attendance-data",
                        clazz = stylesOf(CONTAINER_TAB, USER),
                        text = "No attendance data available"
                    )
                )
            )
        }

        // Create attendance containers
        val attendanceContainers = attendances.map { attendance ->
            createAttendanceContainer(attendance)
        }.toTypedArray()

        // Create attendances container
        val attendancesContainer = createDiv(
            id = "dynamic-attendances-container",
            clazz = stylesOf(USERS_CONTAINER),
            children = attendanceContainers
        )

        return createDiv(
            id = ElementID.RIGHT_SECTION.toString(),
            clazz = stylesOf(SECTION, RIGHT),
            children = arrayOf(header, attendancesContainer)
        )
    }

    private fun createUserContainer(user: User): HTMLDivElement
    {
        val userNameHeader = createHeader(
            id = null,
            text = "${user.firstName} ${user.lastName}",
            level = 2
        )
        userNameHeader.id = "${user.attendooUsername}-name"

        val userDetails = createDiv(
            id = "${user.attendooUsername}-details",
            clazz = stylesOf(USER_DETAILS),
            children = arrayOf(
                userNameHeader,
                createDiv(
                    id = "${user.attendooUsername}-department",
                    clazz = stylesOf(CONTAINER_FIELD),
                    text = "Department: ${user.userDepartment}"
                ),
                createDiv(
                    id = "${user.attendooUsername}-status",
                    clazz = stylesOf(CONTAINER_FIELD),
                    text = "Status: ${user.userStatus}"
                ),
                createDiv(
                    id = "${user.attendooUsername}-role",
                    clazz = stylesOf(CONTAINER_FIELD),
                    text = "Status until: TBD"
                )
            )
        )

        return createDiv(
            id = "${user.attendooUsername}-container",
            clazz = stylesOf(CONTAINER_TAB, USER),
            child = userDetails
        )
    }

    private fun createAttendanceContainer(attendance: UserAttendanceWithInfoDTO): HTMLDivElement
    {
        val userNameHeader = createHeader(
            id = null,
            text = "${attendance.firstName} ${attendance.lastName}",
            level = 2
        )
        userNameHeader.id = "${attendance.attendooUsername}-attendance-name"

        // Use placeholder dates for now - to avoid LocalDate reference issues
        val startDateFormatted = "[start date]"
        val endDateFormatted = "[end date]"

        val userDetails = createDiv(
            id = "${attendance.attendooUsername}-attendance-details",
            clazz = stylesOf(USER_DETAILS),
            children = arrayOf(
                userNameHeader,
                createDiv(
                    id = "${attendance.attendooUsername}-attendance-department",
                    clazz = stylesOf(CONTAINER_FIELD),
                    text = "Department: ${attendance.userDepartment}"
                ),
                createDiv(
                    id = "${attendance.attendooUsername}-attendance-status",
                    clazz = stylesOf(CONTAINER_FIELD),
                    text = "Status: ${attendance.userStatus}"
                ),
                createDiv(
                    id = "${attendance.attendooUsername}-attendance-from",
                    clazz = stylesOf(CONTAINER_FIELD),
                    text = "Status from: $startDateFormatted"
                ),
                createDiv(
                    id = "${attendance.attendooUsername}-attendance-to",
                    clazz = stylesOf(CONTAINER_FIELD),
                    text = "Status to: $endDateFormatted"
                )
            )
        )

        return createDiv(
            id = "${attendance.attendooUsername}-attendance-container",
            clazz = stylesOf(CONTAINER_TAB, USER),
            child = userDetails
        )
    }

    override fun eraseDynamicElement()
    {
        currentlyActiveHTMLElements.forEach {
            it.remove()
        }
        currentlyActiveHTMLElements.clear()
    }
}
