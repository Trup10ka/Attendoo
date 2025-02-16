package com.trup10ka.attendoo.pages

enum class ElementID
{
    ATTENDOO,
    
    /* === Sidebar buttons === */
    DASHBOARD_BUTTON,
    USERS_BUTTON,
    REQUESTS_BUTTON,
    REVIEW_REQUESTS_BUTTON,
    
    /* === Create user === */
    CREATE_USER_FORM_CREDENTIALS,
    CREATE_USER_FORM_ATTRIBUTES,
    
    MAIN;
    
    override fun toString() = this.name.lowercase().replace('_', '-')
}
