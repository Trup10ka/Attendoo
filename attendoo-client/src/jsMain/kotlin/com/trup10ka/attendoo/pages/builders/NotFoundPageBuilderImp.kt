package com.trup10ka.attendoo.pages.builders

import com.trup10ka.attendoo.pages.constant.ElementID.*
import com.trup10ka.attendoo.pages.constant.StyleClass.CENTER
import com.trup10ka.attendoo.pages.constant.StyleClass.INNER_CONTAINER
import com.trup10ka.attendoo.util.createDiv
import com.trup10ka.attendoo.util.createHeader
import com.trup10ka.attendoo.util.stylesOf
import org.w3c.dom.HTMLElement

class NotFoundPageBuilderImp() : NotFoundPageBuilder
{
    override val currentlyActiveHTMLElements: MutableSet<HTMLElement> = mutableSetOf()

    override fun buildDynamicElement(appender: HTMLElement?)
    {
        val notFoundDiv = createDiv(
            id = NOT_FOUND_DIV,
            clazz = stylesOf(INNER_CONTAINER, CENTER),
            child = createHeader(
                text = "Page not found :/",
                level = 1
            )
        )

        appender!!.appendChild(notFoundDiv)
        currentlyActiveHTMLElements.add(notFoundDiv)
    }

    override fun eraseDynamicElement()
    {
        currentlyActiveHTMLElements.forEach {
            it.remove()
        }
        currentlyActiveHTMLElements.clear()
    }
}
