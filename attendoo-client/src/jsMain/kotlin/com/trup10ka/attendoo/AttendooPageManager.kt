package com.trup10ka.attendoo

import com.trup10ka.attendoo.fetch.AttendooKtorHttpClient
import com.trup10ka.attendoo.pages.AttendooLoginPage
import com.trup10ka.attendoo.pages.AttendooPage
import com.trup10ka.attendoo.uri.AttendooURIHandler
import com.trup10ka.attendoo.pages.PageType.LOGIN_PAGE
import com.trup10ka.attendoo.pages.PageType.DASHBOARD_PAGE
import com.trup10ka.attendoo.pages.PageType.NOT_FOUND_PAGE
import com.trup10ka.attendoo.pages.PageType
import com.trup10ka.attendoo.util.addAll

class AttendooPageManager(
    private val uriHandler: AttendooURIHandler
)
{
    private val pages = mutableMapOf<PageType, AttendooPage>()

    lateinit var currentPage: AttendooPage

    fun initPageManager(ktorClient: AttendooKtorHttpClient)
    {
        initPageList(ktorClient)
    }

    fun switchToPage(page: AttendooPage)
    {
        if (::currentPage.isInitialized)
        {
            currentPage.hide()
        }
        currentPage = page
        currentPage.show()
    }

    fun getPage(): AttendooPage
    {
        val path = uriHandler.getPagePath()
        val page = matchPathToPage(path)
        return page
    }

    private fun initPageList(ktorClient: AttendooKtorHttpClient)
    {
        val loginPage = AttendooLoginPage(LOGIN_PAGE, ktorClient)
        val dashboardPage = AttendooLoginPage(DASHBOARD_PAGE, ktorClient)

        pages.addAll(
            loginPage.pageType to loginPage,
            dashboardPage.pageType to dashboardPage
        )
    }
    
    private fun matchPathToPage(path: String): AttendooPage
    {
        return when (path)
        {
            LOGIN_PAGE.pageRouteName -> pages[LOGIN_PAGE]!!
            DASHBOARD_PAGE.pageRouteName -> pages[DASHBOARD_PAGE]!!
            else -> pages[NOT_FOUND_PAGE]!!
        }
    }
}
