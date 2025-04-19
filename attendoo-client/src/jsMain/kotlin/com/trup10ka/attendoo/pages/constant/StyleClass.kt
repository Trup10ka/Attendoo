package com.trup10ka.attendoo.pages.constant

enum class StyleClass
{
    ATTENDOO,

    /* === Global === */
    FORM_FIELD,
    FORM_FIELD_GROUP,
    SUBMIT_BUTTON,
    INNER_CONTAINER,
    CONTAINER_HEADER,
    CONTAINER_TAB,
    MINI_CONTAINER_HEADER,
    ONE_LINE_CONTAINER,
    EMPLOYEE_NAME_CONTAINER,
    AVATAR,
    ACTIONS_CONTAINER,

    /* === Login ===  */
    LOGIN_PANE,
    LOGIN_FORM,
    LOGIN_PANE_HEADER_TEXT,
    LOGIN_REGISTER_ACTION,

    /* === Request === */
    REQUEST_CONTAINER,
    REQUEST,

    /* === Create user === */
    CREATE_USER_CONTAINER,
    CREATE_USER_CONTAINER_SPLITTER,
    CREATE_USER_FORM,

    /* === Dashboard === */

    /* === Users === */
    USERS_CONTAINER,
    USER,
    USER_INFO,
    USER_CREDENTIALS,

    /* === Google icons === */
    ICON,
    MATERIAL_SYMBOLS_OUTLINED;


    override fun toString() = this.name.lowercase().replace('_', '-')
}
