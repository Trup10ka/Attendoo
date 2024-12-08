package com.trup10ka.attendoo.pages

interface AttendooPage
{
    val pageType: PageType

    fun init()
    fun show()
    fun hide()
}
