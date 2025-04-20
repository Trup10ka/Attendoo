package com.trup10ka.attendoo.pages.constant

enum class ElementID
{
    ATTENDOO,
    BURGER_BUTTON,

    /* === Sidebar buttons === */
    SIDEBAR_CONTAINER,
    SIDEBAR,
    DASHBOARD_BUTTON,
    USERS_BUTTON,
    REQUESTS_BUTTON,
    CREATE_USER_BUTTON,

    /* === Create user === */
    CREATE_USER_PANE,
    CREATE_USER_CONTAINER_SPLITTER,
    CREATE_USER_FORM_CREDENTIALS,
    CREATE_USER_FORM_ATTRIBUTES,
    CREATE_USER_FIRST_NAME,
    CREATE_USER_LAST_NAME,
    CREATE_USER_EMAIL,
    CREATE_USER_PHONE,
    CREATE_USER_DEPARTMENT,
    CREATE_USER_NEW_DEPARTMENT,
    CREATE_USER_NEW_DEPARTMENT_DIV,
    CREATE_USER_ROLE,
    CREATE_USER_NEW_ROLE,
    CREATE_USER_NEW_ROLE_DIV,
    CREATE_USER_USERNAME,
    CREATE_USER_PASSWORD,
    CREATE_USER_SUBMIT,

    /* === Login page === */
    LOGIN_SUBMIT_BUTTON,
    USERNAME_INPUT_FIELD,
    PASSWORD_INPUT_FIELD,

    /* === Dashboard page === */
    DASHBOARD_CONTAINER,
    LEFT_SECTION,
    NO_USER_DATA,
    YOUR_STATUS_HEADER,
    CURRENT_USER_NAME,
    CURRENT_USER_DETAILS,
    CURRENT_USER_DEPARTMENT,
    CURRENT_USER_STATUS,
    CURRENT_USER_ROLE,
    CURRENT_USER_CONTAINER,
    RIGHT_SECTION,
    AVAILABLE_USERS_HEADER,
    USERS_CONTAINER,

    /* === Users page === */
    MAIN_USERS_CONTAINER,

    NOT_FOUND_DIV,
    MAIN;

    override fun toString() = this.name.lowercase().replace('_', '-')


    companion object
    {
        fun fromID(id: String): ElementID?
        {
            return try
            {
                valueOf(id.uppercase().replace('-', '_'))
            }
            catch (_: Exception)
            {
                null
            }
        }
    }
}
