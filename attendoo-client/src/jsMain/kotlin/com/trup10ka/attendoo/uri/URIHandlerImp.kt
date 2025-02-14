package com.trup10ka.attendoo.uri

import kotlinx.browser.window

class URIHandlerImp() : URIHandler
{
    lateinit var currentPagePath: String

    override fun initUriHandler()
    {
        currentPagePath = getPagePath()
    }

    override fun getPagePath(): String
    {
        return window.location.pathname.substringAfter(window.location.host)
    }

    override fun getFullPath(): String
    {
        return window.location.pathname
    }

    override fun updateURI(pagePath: String)
    {
        window.history.pushState(null, "", pagePath)
    }
}
