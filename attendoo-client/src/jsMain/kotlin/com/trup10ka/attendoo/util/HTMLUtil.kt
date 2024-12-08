package com.trup10ka.attendoo.util

import kotlinx.browser.document
import org.w3c.dom.HTMLButtonElement
import org.w3c.dom.HTMLDivElement
import org.w3c.dom.HTMLElement

fun getElementByID(id: String) = document.getElementById(id) as HTMLElement?

fun getButtonByID(id: String) = getElementByID(id) as HTMLButtonElement?

fun getDivByID(id: String) = getElementByID(id) as HTMLDivElement?

fun toggleElementVisibility(element: HTMLElement)
{
    element.style.display = if (element.style.display == "none") "block" else "none"
}
