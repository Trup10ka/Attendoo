package com.trup10ka.attendoo

import com.trup10ka.attendoo.fetch.KtorHttpClient
import com.trup10ka.attendoo.pages.LoginPage
import com.trup10ka.attendoo.pages.Page
import com.trup10ka.attendoo.uri.URIHandler
import com.trup10ka.attendoo.pages.constant.PageType.LOGIN_PAGE
import com.trup10ka.attendoo.pages.constant.PageType.DASHBOARD_PAGE
import com.trup10ka.attendoo.pages.constant.PageType.NOT_FOUND_PAGE
import com.trup10ka.attendoo.pages.constant.PageType
import com.trup10ka.attendoo.util.addAll

class AttendooPageManager(
    private val uriHandler: URIHandler
)
{
    lateinit var currentPage: Page

    private val pages = mutableMapOf<PageType, Page>()
    
    fun initPageManager(ktorClient: KtorHttpClient)
    {
        initPageList(ktorClient)
    }

    fun switchToPage(page: Page)
    {
        if (::currentPage.isInitialized)
        {
            currentPage.hide()
        }
        currentPage = page
        uriHandler.updateURI(page.pageType.getPageRoute())
        currentPage.show()
    }

    fun getPage(): Page
    {
        val path = uriHandler.getPagePath()
        val page = matchPathToPage(path)
        return page
    }

    private fun initPageList(ktorClient: KtorHttpClient)
    {
        val loginPage = LoginPage(LOGIN_PAGE, ktorClient)
        val dashboardPage = LoginPage(DASHBOARD_PAGE, ktorClient)

        pages.addAll(
            loginPage.pageType to loginPage,
            dashboardPage.pageType to dashboardPage
        )
    }
    
    private fun matchPathToPage(path: String): Page
    {
        return when (path)
        {
            LOGIN_PAGE.pageRouteName -> pages[LOGIN_PAGE]!!
            DASHBOARD_PAGE.pageRouteName -> pages[DASHBOARD_PAGE]!!
            else -> pages[NOT_FOUND_PAGE]!!
        }
    }
}
