package com.trup10ka.attendoo.pages

import com.trup10ka.attendoo.AttendooPageManager
import com.trup10ka.attendoo.fetch.HttpClient
import com.trup10ka.attendoo.pages.builders.UsersPageBuilderImp
import com.trup10ka.attendoo.pages.constant.PageType

class UsersPage(
    override val pageType: PageType,
    override val pageManager: AttendooPageManager,
    private val httpClient: HttpClient
) : Page
{
    override val pageBuilder = UsersPageBuilderImp()
    
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
