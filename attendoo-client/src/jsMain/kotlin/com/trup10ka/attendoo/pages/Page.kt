package com.trup10ka.attendoo.pages

interface Page
{
    val pageType: PageType

    fun init()
    fun show()
    fun hide()
}
