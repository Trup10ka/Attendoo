package com.trup10ka.attendoo.pages.constant

enum class StyleClass
{
    ATTENDOO,
    SIDEBAR_BUTTON,

    /* === Global === */
    FORM_FIELD,
    FORM_FIELD_GROUP,
    SUBMIT_BUTTON,
    INNER_CONTAINER,
    CONTAINER_HEADER,
    CONTAINER_TAB,
    CONTAINER_FIELD,
    MINI_CONTAINER_HEADER,
    ONE_LINE_CONTAINER,
    EMPLOYEE_NAME_CONTAINER,
    AVATAR,
    CENTER,
    ACTIONS_CONTAINER,
    ORANGE,
    HEADER_MARGIN,
    FULL_WIDTH,

    /* === Login ===  */
    LOGIN_PANE,
    LOGIN_FORM,
    LOGIN_PANE_HEADER_TEXT,
    LOGIN_REGISTER_ACTION,

    /* === Request === */
    REQUESTS_CONTAINER,
    REQUEST,
    REQUEST_INFO,
    REQUEST_FORM,

    /* === Create user === */
    CREATE_USER_CONTAINER,
    CREATE_USER_CONTAINER_SPLITTER,
    CREATE_USER_FORM,

    /* === Dashboard === */
    DASHBOARD_CONTAINER,
    SECTION,
    LEFT,
    RIGHT,
    USER_DETAILS,
    USERS_CONTAINER,

    /* === Users === */
    USER,
    USER_INFO,
    USER_CREDENTIALS,

    /* === Google icons === */
    ICON,
    MATERIAL_SYMBOLS_OUTLINED;


    override fun toString() = this.name.lowercase().replace('_', '-')
}
