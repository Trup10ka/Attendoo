package com.trup10ka.attendoo.pages

import com.trup10ka.attendoo.fetch.HttpClient
import com.trup10ka.attendoo.pages.builders.DashboardPageBuilderImp
import com.trup10ka.attendoo.pages.constant.PageType

class DashboardPage(
    override val pageType: PageType,
    private val httpClient: HttpClient
) : Page
{
    override val pageBuilder = DashboardPageBuilderImp()
    
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
