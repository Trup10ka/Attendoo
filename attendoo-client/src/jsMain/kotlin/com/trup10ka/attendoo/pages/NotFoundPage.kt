package com.trup10ka.attendoo.pages

import com.trup10ka.attendoo.AttendooPageManager
import com.trup10ka.attendoo.pages.builders.NotFoundPageBuilderImp
import com.trup10ka.attendoo.pages.builders.PageBuilder
import com.trup10ka.attendoo.pages.constant.PageType

class NotFoundPage(
    override val pageType: PageType,
    override val pageManager: AttendooPageManager,
) : Page
{
    override val pageBuilder: PageBuilder = NotFoundPageBuilderImp()
    
    override fun init()
    {
        TODO("Not yet implemented")
    }
    
    override fun show()
    {
        TODO("Not yet implemented")
    }
    
    override fun hide()
    {
        TODO("Not yet implemented")
    }
}
