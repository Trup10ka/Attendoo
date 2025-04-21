package com.trup10ka.attendoo.pages.builders

import com.trup10ka.attendoo.data.Request
import org.w3c.dom.HTMLElement

interface RequestPageBuilder : PageBuilder
{
    fun buildRequestContainer(appender: HTMLElement? = null, request: Request)

    fun buildNoRequestsFoundMessage(appender: HTMLElement? = null)

    fun buildRequestCreationForm(appender: HTMLElement? = null, usernames: List<String>, statuses: List<String>)
}
