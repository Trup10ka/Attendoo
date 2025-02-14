package com.trup10ka.attendoo.pages

enum class PageType(val pageRouteName: String)
{
    ROOT_PAGE("root"),
    LOGIN_PAGE("login"),
    DASHBOARD_PAGE("dashboard"),
    ATTENDANCE_PAGE("attendance"),
    SETTINGS_PAGE("settings"),
    NOT_FOUND_PAGE("not-found"),
    ERROR_PAGE("error");
    
    fun getPageRoute(): String
    {
        if (this == ROOT_PAGE)
            return "/"
        
        return "/$pageRouteName"
    }
}
