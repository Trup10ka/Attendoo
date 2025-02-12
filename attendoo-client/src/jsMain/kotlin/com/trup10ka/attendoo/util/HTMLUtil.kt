package com.trup10ka.attendoo.util

import kotlinx.browser.document
import org.w3c.dom.HTMLButtonElement
import org.w3c.dom.HTMLDivElement
import org.w3c.dom.HTMLElement
import org.w3c.dom.HTMLFormElement
import org.w3c.dom.HTMLHeadingElement
import org.w3c.dom.HTMLInputElement
import org.w3c.dom.HTMLLabelElement
import org.w3c.dom.HTMLSpanElement

fun getElementByID(id: String) = document.getElementById(id) as HTMLElement?

fun getButtonByID(id: String) = getElementByID(id) as HTMLButtonElement?

fun getDivByID(id: String) = getElementByID(id) as HTMLDivElement?

/**
 * Creates an element of the given type with the given ID and class.
 *
 * @param tag The type of element to create.
 * @param id The ID to assign to the element.
 * @param clazz The class to assign to the element.
 * @return The created element.
 *
 * @param T The type of the element to create. NOTE: This function has suppressed unchecked
 * casts since all the HTML elements are a subclass of HTMLElement.
 */
@Suppress("UNCHECKED_CAST")
fun <T> createElement(tag: String, id: String? = null, clazz: String? = null): T where T : HTMLElement
{
    val element = document.createElement(tag) as T
    id?.let { element.id = it }
    clazz?.let { element.className = it }
    return element
}

fun createDiv(id: String? = null, clazz: String? = null, vararg children: HTMLElement): HTMLDivElement
{
    val div = createElement<HTMLDivElement>("div", id, clazz)
    children.forEach { div.appendChild(it) }
    return div
}

fun createSpan(id: String? = null, clazz: String? = null, text: String): HTMLSpanElement
{
    val span = createElement<HTMLSpanElement>("span", id, clazz)
    span.textContent = text
    return span
}

fun createForm(id: String? = null, clazz: String? = null, vararg children: HTMLElement): HTMLFormElement
{
    val form = createElement<HTMLFormElement>("form", id, clazz)
    children.forEach { form.appendChild(it) }
    return form
}

fun createButton(id: String? = null, clazz: String? = null, text: String): HTMLButtonElement
{
    val button = createElement<HTMLButtonElement>("button", id, clazz)
    button.textContent = text
    return button
}

fun createHeader(id: String? = null, clazz: String? = null, text: String, level: Int = 1): HTMLHeadingElement
{
    val header = createElement<HTMLHeadingElement>("h$level", id, clazz)
    header.textContent = text
    return header
}

fun createWrappedInput(id: String? = null, clazz: String? = null, type: String, placeholder: String? = null): HTMLLabelElement
{
    val wrappingLabel = createElement<HTMLLabelElement>("label", clazz = clazz)
    val input = createElement<HTMLInputElement>("input", id)
    input.type = type
    placeholder?.let { input.setAttribute("placeholder", it) }
    wrappingLabel.appendChild(input)
    return wrappingLabel
}

fun toggleElementVisibility(element: HTMLElement)
{
    element.style.display = if (element.style.display == "none") "block" else "none"
}
