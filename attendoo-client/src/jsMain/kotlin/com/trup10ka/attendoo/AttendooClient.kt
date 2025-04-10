package com.trup10ka.attendoo

import com.trup10ka.attendoo.auth.AttendooJWTAuth
import com.trup10ka.attendoo.fetch.KtorHttpClient
import com.trup10ka.attendoo.pages.constant.ElementID
import com.trup10ka.attendoo.uri.URIHandler
import com.trup10ka.attendoo.uri.URIHandlerImp
import com.trup10ka.attendoo.util.getButtonByID
import org.w3c.dom.HTMLButtonElement
import com.trup10ka.attendoo.pages.constant.ElementID.*
import com.trup10ka.attendoo.pages.constant.PageType.*
import com.trup10ka.attendoo.util.getDivByID
import com.trup10ka.attendoo.util.launchDefaultCoroutine
import com.trup10ka.attendoo.util.mapButtonToPage
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
        pageManager.initPageManager(ktorClient)
        uriHandler.initUriHandler()
        
        initBurgerButtonListener()
        
        launchDefaultCoroutine {
            
            if (!jwtAuthenticator.isAuthenticated())
            {
                pageManager.showLoginPage()
                return@launchDefaultCoroutine
            }
            
            initButtonListeners()
            
            displayPage()
        }
    }

    private fun displayPage()
    {
        val page = pageManager.getCurrentPage()
        pageManager.switchToPage(page)
    }
    
    private fun initButtonListeners()
    {
        val buttons = arrayOf(
            getButtonByID(DASHBOARD_BUTTON),
            getButtonByID(USERS_BUTTON),
            getButtonByID(REQUESTS_BUTTON),
            getButtonByID(CREATE_USER_BUTTON)
        )
        
        if (buttons.contains(null))
        {
            showErrorPage("One or more buttons not found")
            throw IllegalStateException("One or more buttons not found")
        }
        
        attendooSidebarButtons.addAll(buttons.map { it!! })
        
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
