package com.trup10ka.attendoo

import com.trup10ka.attendoo.fetch.KtorHttpClient
import com.trup10ka.attendoo.uri.URIHandler
import com.trup10ka.attendoo.uri.URIHandlerImp
import com.trup10ka.attendoo.util.getButtonByID
import org.w3c.dom.HTMLButtonElement
import com.trup10ka.attendoo.pages.ElementID.*
import com.trup10ka.attendoo.pages.PageType.*

class AttendooClient
{
    private val uriHandler: URIHandler = URIHandlerImp()

    private val pageManager = AttendooPageManager(uriHandler)

    private val ktorClient = KtorHttpClient()
    
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
        val page = pageManager.getPage()
        pageManager.switchToPage(page)
    }
    
    private fun initButtonListeners()
    {
        val buttons = arrayOf(
            getButtonByID(DASHBOARD_BUTTON.toString()),
            getButtonByID(USERS_BUTTON.toString()),
            getButtonByID(REQUEST_BUTTON.toString()),
            getButtonByID(REVIEW_REQUEST_BUTTON.toString())
        )
        
        if (buttons.contains(null))
        {
            throw IllegalStateException("One or more buttons not found")
        }
        
        attendooSidebarButtons.forEach { button ->
            button.addEventListener("click", {
                val page = pageManager.getPage()
                pageManager.switchToPage(page)
            })
        }
    }
    
    private fun showErrorPage(errorMessage: String)
    {
        uriHandler.updateURI(ERROR_PAGE.getPageRoute())
    }
}
