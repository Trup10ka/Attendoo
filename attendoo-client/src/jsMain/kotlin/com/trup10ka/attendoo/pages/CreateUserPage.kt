package com.trup10ka.attendoo.pages

import com.trup10ka.attendoo.fetch.KtorHttpClient
import com.trup10ka.attendoo.pages.builders.CreateUserPageBuilderImp
import com.trup10ka.attendoo.pages.constant.PageType

class CreateUserPage(
    override val pageType: PageType,
    private val httpClient: KtorHttpClient
) : Page
{
    override val pageBuilder = CreateUserPageBuilderImp()
    
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
