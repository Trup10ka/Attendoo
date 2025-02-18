package com.trup10ka.attendoo.pages.constant

enum class ElementID
{
    ATTENDOO,
    
    /* === Sidebar buttons === */
    DASHBOARD_BUTTON,
    USERS_BUTTON,
    REQUESTS_BUTTON,
    CREATE_USER_BUTTON,
    
    /* === Create user === */
    CREATE_USER_FORM_CREDENTIALS,
    CREATE_USER_FORM_ATTRIBUTES,
    
    MAIN;
    
    override fun toString() = this.name.lowercase().replace('_', '-')
}
