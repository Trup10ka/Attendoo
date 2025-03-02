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
import com.trup10ka.attendoo.util.mapButtonToPage

class AttendooClient
{
    private val uriHandler: URIHandler = URIHandlerImp()

    private val ktorClient = KtorHttpClient()
    
    private val pageManager = AttendooPageManager(uriHandler, ktorClient)
    
    private val attendooSidebarButtons = mutableListOf<HTMLButtonElement>()

    fun init()
    {
        pageManager.initPageManager(ktorClient)
        uriHandler.initUriHandler()
        
        initButtonListeners()
        
        displayPage()
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
