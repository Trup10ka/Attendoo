package com.trup10ka.attendoo.pages.constant

enum class ElementID
{
    ATTENDOO,
    BURGER_BUTTON,
    
    /* === Sidebar buttons === */
    SIDEBAR_CONTAINER,
    DASHBOARD_BUTTON,
    USERS_BUTTON,
    REQUESTS_BUTTON,
    CREATE_USER_BUTTON,
    
    /* === Create user === */
    CREATE_USER_FORM_CREDENTIALS,
    CREATE_USER_FORM_ATTRIBUTES,
    
    /* === Login page === */
    LOGIN_SUBMIT_BUTTON,
    USERNAME_INPUT_FIELD,
    PASSWORD_INPUT_FIELD,
    
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
