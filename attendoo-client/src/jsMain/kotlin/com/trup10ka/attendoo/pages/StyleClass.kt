package com.trup10ka.attendoo.pages

enum class StyleClass
{
    ATTENDOO,

    /* === Global === */
    FORM_FIELD,
    SUBMIT_BUTTON,
    INNER_CONTAINER,
    CONTAINER_HEADER,

    /* === Login ===  */
    LOGIN_PANE,
    LOGIN_FORM,
    LOGIN_PANE_HEADER_TEXT,
    LOGIN_REGISTER_ACTION,
    
    /* === Create user === */
    CREATE_USER_CONTAINER,
    CREATE_USER_CONTAINER_SPLITTER,
    CREATE_USER_FORM;
    
    
    override fun toString() = this.name.lowercase().replace('_', '-')
}
