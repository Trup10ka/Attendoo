package com.trup10ka.attendoo

import kotlinx.browser.document
import org.w3c.dom.HTMLElement

fun main()
{
    val attendooClient = AttendooClient()
    attendooClient.init()
    document.querySelector(".hamburger")?.addEventListener("click", { toggleSidebar() })
}

fun toggleSidebar() {
    val sidebar = document.querySelector(".sidebar") as HTMLElement
    sidebar.style.display = if (sidebar.style.display === "none")  "block" else {
        "none"
    }
}
