package com.trup10ka.attendoo.pages

enum class ElementID
{
    ATTENDOO,
    
    /* === Sidebar buttons === */
    DASHBOARD_BUTTON,
    USERS_BUTTON,
    REQUEST_BUTTON,
    REVIEW_REQUEST_BUTTON,
    
    /* === Create user === */
    CREATE_USER_FORM_CREDENTIALS,
    CREATE_USER_FORM_ATTRIBUTES,
    
    MAIN;
    
    override fun toString() = this.name.lowercase().replace('_', '-')
}
