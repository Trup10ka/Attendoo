package com.trup10ka.attendoo.util

import com.trup10ka.attendoo.data.SelectOption
import com.trup10ka.attendoo.pages.ElementID
import kotlinx.browser.document
import kotlinx.dom.addClass
import org.w3c.dom.HTMLButtonElement
import org.w3c.dom.HTMLDivElement
import org.w3c.dom.HTMLElement
import org.w3c.dom.HTMLFormElement
import org.w3c.dom.HTMLHeadingElement
import org.w3c.dom.HTMLInputElement
import org.w3c.dom.HTMLLabelElement
import org.w3c.dom.HTMLOptionElement
import org.w3c.dom.HTMLSelectElement
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
fun <T> createElement(tag: String, id: ElementID? = null, clazz: Array<String>? = null): T where T : HTMLElement
{
    val element = document.createElement(tag) as T
    id?.let { element.id = it.toString() }
    clazz?.let { element.addClass(*clazz) }
    return element
}

fun createDiv(id: ElementID? = null, clazz: Array<String>? = null, vararg children: HTMLElement): HTMLDivElement
{
    val div = createElement<HTMLDivElement>("div", id, clazz)
    children.forEach { div.appendChild(it) }
    return div
}

fun createSpan(id: ElementID? = null, clazz: Array<String>? = null, text: String): HTMLSpanElement
{
    val span = createElement<HTMLSpanElement>("span", id, clazz)
    span.textContent = text
    return span
}

fun createForm(id: ElementID? = null, clazz: Array<String>? = null, vararg children: HTMLElement): HTMLFormElement
{
    val form = createElement<HTMLFormElement>("form", id, clazz)
    children.forEach { form.appendChild(it) }
    return form
}

fun createButton(id: ElementID? = null, clazz: Array<String>? = null, text: String): HTMLButtonElement
{
    val button = createElement<HTMLButtonElement>("button", id, clazz)
    button.textContent = text
    return button
}

fun createHeader(id: ElementID? = null, clazz: Array<String>? = null, text: String, level: Int = 1): HTMLHeadingElement
{
    val header = createElement<HTMLHeadingElement>("h$level", id, clazz)
    header.textContent = text
    return header
}

fun createWrappedInput(id: ElementID? = null, clazz: Array<String>? = null, type: String, placeholder: String? = null): HTMLLabelElement
{
    val wrappingLabel = createElement<HTMLLabelElement>("label", clazz = clazz)
    val input = createElement<HTMLInputElement>("input", id)
    input.type = type
    placeholder?.let { input.setAttribute("placeholder", it) }
    wrappingLabel.appendChild(input)
    return wrappingLabel
}

fun createSelectWithOptions(id: ElementID? = null, clazz: Array<String>, options: Array<SelectOption>): HTMLSelectElement
{
    val select = createElement<HTMLSelectElement>("select", id, clazz)
    options.forEach {
        val option = createElement<HTMLOptionElement>("option")
        option.value = it.value
        option.textContent = it.label
        select.appendChild(option)
    }
    return select
}

fun toggleElementVisibility(element: HTMLElement)
{
    element.style.display = if (element.style.display == "none") "block" else "none"
}
