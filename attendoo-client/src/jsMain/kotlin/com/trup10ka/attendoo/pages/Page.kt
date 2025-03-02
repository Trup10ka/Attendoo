package com.trup10ka.attendoo.pages

import com.trup10ka.attendoo.pages.builders.PageBuilder
import com.trup10ka.attendoo.pages.constant.PageType

interface Page
{
    val pageType: PageType
    
    val pageBuilder: PageBuilder

    fun init()
    fun show()
    fun hide()
}
