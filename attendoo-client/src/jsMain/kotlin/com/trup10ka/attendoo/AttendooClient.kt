package com.trup10ka.attendoo

import com.trup10ka.attendoo.auth.AttendooJWTAuth
import com.trup10ka.attendoo.auth.AuthenticationStatus
import com.trup10ka.attendoo.fetch.KtorHttpClient
import com.trup10ka.attendoo.pages.constant.ElementID
import com.trup10ka.attendoo.uri.URIHandler
import com.trup10ka.attendoo.uri.URIHandlerImp
import com.trup10ka.attendoo.util.getButtonByID
import org.w3c.dom.HTMLButtonElement
import com.trup10ka.attendoo.pages.constant.ElementID.*
import com.trup10ka.attendoo.pages.constant.PageType.*
import com.trup10ka.attendoo.pages.constant.StyleClass
import com.trup10ka.attendoo.pages.constant.StyleClass.*
import com.trup10ka.attendoo.util.createButton
import com.trup10ka.attendoo.util.getDivByID
import com.trup10ka.attendoo.util.launchDefaultCoroutine
import com.trup10ka.attendoo.util.mapButtonToPage
import com.trup10ka.attendoo.util.stylesOf
import kotlinx.browser.window

class AttendooClient
{
    private val uriHandler: URIHandler = URIHandlerImp()
    
    private val ktorClient = KtorHttpClient()
    
    private val jwtAuthenticator = AttendooJWTAuth(ktorClient)
    
    private val pageManager = AttendooPageManager(uriHandler, ktorClient)
    
    private val attendooSidebarButtons = mutableListOf<HTMLButtonElement>()
    
    private var isSidebarToggled = true
    
    fun init()
    {
        pageManager.initPageManager(ktorClient, this)
        uriHandler.initUriHandler()
        
        initBurgerButtonListener()
        
        launchDefaultCoroutine {
            val authStatus = jwtAuthenticator.isAuthenticated()
            
            if (authStatus == AuthenticationStatus.NOT_AUTHENTICATED)
            {
                pageManager.showLoginPage()
                return@launchDefaultCoroutine
            }
            
            createAndInitButtons(authStatus)
            
            displayPage()
        }
    }
    
    private fun displayPage()
    {
        val page = pageManager.getCurrentPage()
        pageManager.switchToPage(page)
    }
    
    fun createAndInitButtons(authStatus: AuthenticationStatus)
    {
        attendooSidebarButtons.clear()
        
        val sidebar = getDivByID(SIDEBAR) ?: run {
            showErrorPage("Sidebar not found")
            throw IllegalStateException("Sidebar not found")
        }
        
        while (sidebar.firstChild != null)
        {
            sidebar.removeChild(sidebar.firstChild!!)
        }
        
        val dashboardButton = createButton(id = DASHBOARD_BUTTON, clazz = stylesOf(SIDEBAR_BUTTON), text = "Dashboard")
        val requestsButton = createButton(id = REQUESTS_BUTTON, clazz = stylesOf(SIDEBAR_BUTTON), text = "Requests")
        
        sidebar.appendChild(dashboardButton)
        sidebar.appendChild(requestsButton)
        
        attendooSidebarButtons.add(dashboardButton)
        attendooSidebarButtons.add(requestsButton)
        
        if (authStatus == AuthenticationStatus.AUTHENTICATED_ADMIN)
        {
            val usersButton = createButton(id = USERS_BUTTON, clazz = stylesOf(SIDEBAR_BUTTON), text = "Users")
            val createUserButton =
                createButton(id = CREATE_USER_BUTTON, clazz = stylesOf(SIDEBAR_BUTTON), text = "Create user")
            
            sidebar.appendChild(usersButton)
            sidebar.appendChild(createUserButton)
            
            attendooSidebarButtons.add(usersButton)
            attendooSidebarButtons.add(createUserButton)
        }
        
        attendooSidebarButtons.forEach { button ->
            button.addEventListener("click", {
                updateUriByButton(button)
                val page = pageManager.getCurrentPage()
                pageManager.switchToPage(page)
            })
        }
    }
    
    private fun initBurgerButtonListener()
    {
        val burgerButton = getDivByID(BURGER_BUTTON)
        val sidebarContainer = getDivByID(SIDEBAR_CONTAINER)
        
        if (burgerButton == null)
        {
            showErrorPage("Burger button not found")
            throw IllegalStateException("Burger button not found")
        }
        
        if (sidebarContainer == null)
        {
            showErrorPage("Sidebar container not found")
            throw IllegalStateException("Sidebar container not found")
        }
        
        burgerButton.addEventListener("click", {
            if (isSidebarToggled)
            {
                sidebarContainer.classList.add("hidden")
                window.setTimeout(
                    { sidebarContainer.classList.add("display-none") },
                    500
                )
            }
            else
            {
                sidebarContainer.classList.remove("display-none")
                window.requestAnimationFrame {
                    sidebarContainer.classList.remove("hidden")
                }
            }
            isSidebarToggled = !isSidebarToggled
        })
    }
    
    private fun updateUriByButton(button: HTMLButtonElement)
    {
        val pageId = ElementID.fromID(button.id)
        if (pageId == null)
            throw IllegalStateException("Button ID not found")
        
        val pageType = mapButtonToPage(pageId)
        if (pageType == null)
            throw IllegalStateException("Page not found")
        
        uriHandler.updateURI(pageType.pageRoute)
    }
    
    private fun showErrorPage(errorMessage: String)
    {
        uriHandler.updateURI(ERROR_PAGE.pageRoute)
    }
}
