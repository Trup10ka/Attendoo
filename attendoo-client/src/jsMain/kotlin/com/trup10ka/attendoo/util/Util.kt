package com.trup10ka.attendoo.util

import com.trup10ka.attendoo.pages.constant.ElementID
import com.trup10ka.attendoo.pages.constant.ElementID.*
import com.trup10ka.attendoo.pages.constant.PageType

fun mapButtonToPage(buttonID: ElementID): PageType?
{
    return when (buttonID.name)
    {
        DASHBOARD_BUTTON.name -> PageType.DASHBOARD_PAGE
        USERS_BUTTON.name -> PageType.USERS_PAGE
        REQUESTS_BUTTON.name -> PageType.REQUESTS_PAGE
        CREATE_USER_BUTTON.name -> PageType.CREATE_USER_PAGE
        else -> null
    }
}
