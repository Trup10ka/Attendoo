package com.trup10ka.attendoo.pages

import com.trup10ka.attendoo.pages.constant.ElementID
import com.trup10ka.attendoo.pages.constant.ElementID.*
import com.trup10ka.attendoo.pages.constant.PageType

object PageButtonMapper
{
    fun mapButtonToPage(buttonID: ElementID): PageType?
    {
        return when (buttonID)
        {
            DASHBOARD_BUTTON -> PageType.DASHBOARD_PAGE
            USERS_BUTTON -> PageType.USERS_PAGE
            REQUESTS_BUTTON -> PageType.REQUESTS_PAGE
            CREATE_USER_BUTTON -> PageType.CREATE_USER_PAGE
            else -> null
        }
    }
}
