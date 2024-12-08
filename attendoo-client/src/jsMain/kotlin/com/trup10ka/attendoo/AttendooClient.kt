package com.trup10ka.attendoo

import com.trup10ka.attendoo.fetch.AttendooKtorHttpClient
import com.trup10ka.attendoo.uri.AttendooURIHandler
import com.trup10ka.attendoo.uri.AttendooURIHandlerImp

class AttendooClient
{
    private val uriHandler: AttendooURIHandler = AttendooURIHandlerImp()

    private val pageManager = AttendooPageManager(uriHandler)

    private val ktorClient = AttendooKtorHttpClient()

    fun init()
    {
        pageManager.initPageManager(ktorClient)
        uriHandler.initUriHandler()

        displayPage()
    }

    private fun displayPage()
    {
        val page = pageManager.getPage()
        pageManager.switchToPage(page)
    }
}
