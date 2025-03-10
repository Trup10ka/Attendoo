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
