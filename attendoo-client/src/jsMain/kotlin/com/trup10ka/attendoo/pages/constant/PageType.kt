package com.trup10ka.attendoo.pages.constant

enum class PageType(val pageRoute: String)
{
    ROOT_PAGE("/"),
    LOGIN_PAGE("/login"),
    DASHBOARD_PAGE("/dashboard"),
    USERS_PAGE("/users"),
    REQUESTS_PAGE("/requests"),
    CREATE_USER_PAGE("/create-user"),
    SETTINGS_PAGE("/settings"),
    NOT_FOUND_PAGE("/not-found"),
    ERROR_PAGE("/error");
    
    init
    {
        require(pageRoute.startsWith("/")) { "Page route name must not be empty" }
    }
}
