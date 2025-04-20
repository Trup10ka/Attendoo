package com.trup10ka.attendoo.pages

import com.trup10ka.attendoo.AttendooPageManager
import com.trup10ka.attendoo.pages.builders.NotFoundPageBuilderImp
import com.trup10ka.attendoo.pages.builders.PageBuilder
import com.trup10ka.attendoo.pages.constant.PageType
import com.trup10ka.attendoo.util.getDivByID
import com.trup10ka.attendoo.pages.constant.ElementID.MAIN

class NotFoundPage(
    override val pageType: PageType,
    override val pageManager: AttendooPageManager,
) : Page
{
    override val pageBuilder: PageBuilder = NotFoundPageBuilderImp()

    override fun init()
    {
        // No initialization needed for this page
    }

    override fun show()
    {
        pageBuilder.buildDynamicElement(getDivByID(MAIN))
    }

    override fun hide()
    {
        pageBuilder.eraseDynamicElement()
    }
}
