package com.trup10ka.attendoo

import com.trup10ka.attendoo.auth.AttendooJWTAuth
import com.trup10ka.attendoo.auth.Authenticator
import com.trup10ka.attendoo.fetch.KtorHttpClient
import com.trup10ka.attendoo.pages.CreateUserPage
import com.trup10ka.attendoo.pages.DashboardPage
import com.trup10ka.attendoo.pages.LoginPage
import com.trup10ka.attendoo.pages.Page
import com.trup10ka.attendoo.pages.RequestsPage
import com.trup10ka.attendoo.pages.UsersPage
import com.trup10ka.attendoo.uri.URIHandler
import com.trup10ka.attendoo.pages.constant.PageType.*
import com.trup10ka.attendoo.pages.constant.PageType
import com.trup10ka.attendoo.util.addAll

class AttendooPageManager(
    val uriHandler: URIHandler,
    ktorClient: KtorHttpClient,
)
{
    lateinit var currentPage: Page
    
    private val jwtAuthenticator: Authenticator = AttendooJWTAuth(ktorClient)
    
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
        currentPage.show()
    }
    
    fun showLoginPage()
    {
        val loginPage = pages[LOGIN_PAGE]!!
        uriHandler.updateURI(LOGIN_PAGE.pageRoute)
        switchToPage(loginPage)
    }
    
    fun getCurrentPage(): Page
    {
        val path = uriHandler.getPagePath()
        val page = matchPathToPage(path)
        return page
    }
    
    private fun initPageList(ktorClient: KtorHttpClient)
    {
        val loginPage = LoginPage(LOGIN_PAGE, this, jwtAuthenticator)
        val dashboardPage = DashboardPage(DASHBOARD_PAGE, this, ktorClient)
        val usersPage = UsersPage(USERS_PAGE, this, ktorClient)
        val requestsPage = RequestsPage(REQUESTS_PAGE, this, ktorClient)
        val createUserPage = CreateUserPage(CREATE_USER_PAGE, this, ktorClient)
        
        pages.addAll(
            loginPage.pageType to loginPage,
            dashboardPage.pageType to dashboardPage,
            usersPage.pageType to usersPage,
            requestsPage.pageType to requestsPage,
            createUserPage.pageType to createUserPage
        )
    }
    
    private fun matchPathToPage(path: String): Page
    {
        return when (path)
        {
            ROOT_PAGE.pageRoute      -> pages[DASHBOARD_PAGE]!!
            LOGIN_PAGE.pageRoute     -> pages[LOGIN_PAGE]!!
            DASHBOARD_PAGE.pageRoute -> pages[DASHBOARD_PAGE]!!
            USERS_PAGE.pageRoute     -> pages[USERS_PAGE]!!
            REQUESTS_PAGE.pageRoute  -> pages[REQUESTS_PAGE]!!
            else                     -> pages[NOT_FOUND_PAGE]!!
        }
    }
}
