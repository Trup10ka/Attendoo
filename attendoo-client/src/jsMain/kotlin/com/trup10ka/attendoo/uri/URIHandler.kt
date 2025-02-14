package com.trup10ka.attendoo.uri

interface URIHandler
{
    fun initUriHandler()
    fun getPagePath(): String
    fun getFullPath(): String
    fun updateURI(pagePath: String)
}
