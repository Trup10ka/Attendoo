package com.trup10ka.attendoo.pages

import com.trup10ka.attendoo.pages.constant.PageType

interface Page
{
    val pageType: PageType

    fun init()
    fun show()
    fun hide()
}
