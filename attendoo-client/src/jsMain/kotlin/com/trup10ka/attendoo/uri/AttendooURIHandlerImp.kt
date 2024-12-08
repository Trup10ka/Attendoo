package com.trup10ka.attendoo.uri

class AttendooURIHandlerImp() : AttendooURIHandler
{
    lateinit var currentPagePath: String

    override fun initUriHandler()
    {
        currentPagePath = getPagePath()
    }

    override fun getPagePath(): String
    {
        TODO("Not yet implemented")
    }

    override fun getFullPath(): String
    {
        TODO("Not yet implemented")
    }

    override fun updateURI(pagePath: String)
    {
        TODO("Not yet implemented")
    }
}
