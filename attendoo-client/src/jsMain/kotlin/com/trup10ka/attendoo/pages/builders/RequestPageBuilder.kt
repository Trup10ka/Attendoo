package com.trup10ka.attendoo.pages.builders

import com.trup10ka.attendoo.data.Request

interface RequestPageBuilder
{
    fun buildRequestContainer(request: Request)
    fun eraseRequestContainer()
}
