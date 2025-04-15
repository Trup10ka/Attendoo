package com.trup10ka.attendoo.pages.builders

import com.trup10ka.attendoo.data.SelectOption
import org.w3c.dom.HTMLElement
import com.trup10ka.attendoo.pages.constant.StyleClass.*
import com.trup10ka.attendoo.pages.constant.ElementID.*
import com.trup10ka.attendoo.util.stylesOf
import com.trup10ka.attendoo.util.createButton
import com.trup10ka.attendoo.util.createDiv
import com.trup10ka.attendoo.util.createForm
import com.trup10ka.attendoo.util.createHeader
import com.trup10ka.attendoo.util.createSelectWithOptions
import com.trup10ka.attendoo.util.createSpan
import com.trup10ka.attendoo.util.createWrappedInput

class CreateUserPageBuilderImp : CreateUserPageBuilder
{
    override val currentlyActiveHTMLElements = mutableSetOf<HTMLElement>()
    
    override fun buildDynamicContent(appender: HTMLElement?, groupOptions: Array<String>, roleOptions: Array<String>)
    {
        val createUserPane = createDiv(
                clazz = stylesOf(
                    INNER_CONTAINER,
                    CREATE_USER_CONTAINER
                ),
                children = stylesOf(
                    createHeader(text = "Create user", clazz = stylesOf(CONTAINER_HEADER)),
                    createDiv(clazz = stylesOf(CREATE_USER_CONTAINER_SPLITTER),
                        children = arrayOf(
                            createCredentialsForm(),
                            createUserAttributesForm(groupOptions, roleOptions),
                        )
                    ),
                    createSubmitButton()
                )
        )
        
        appender!!.appendChild(createUserPane)
        currentlyActiveHTMLElements.add(createUserPane)
    }
    
    override fun eraseDynamicElement()
    {
        currentlyActiveHTMLElements.forEach {
            it.remove()
        }
        currentlyActiveHTMLElements.clear()
    }
    
    private fun createCredentialsForm(): HTMLElement
    {
        return createForm(
            id = CREATE_USER_FORM_CREDENTIALS,
            clazz = stylesOf(CREATE_USER_FORM),
            children = arrayOf(
                createWrappedInput(clazz = stylesOf(FORM_FIELD), type = "text", placeholder = "First Name (exp. Julia)"),
                createWrappedInput(clazz = stylesOf(FORM_FIELD), type = "text", placeholder = "Last Name (exp. Crook)"),
                createWrappedInput(clazz = stylesOf(FORM_FIELD), type = "email", placeholder = "Email"),
                createWrappedInput(clazz = stylesOf(FORM_FIELD), type = "tel", placeholder = "Tel")
            )
        )
    }
    
    private fun createUserAttributesForm(groupOptions: Array<String>, roleOptions: Array<String>): HTMLElement
    {
        return createForm(
            id = CREATE_USER_FORM_ATTRIBUTES,
            clazz = stylesOf(CREATE_USER_FORM),
            children = arrayOf(
                createSelectWithOptions(
                    clazz = stylesOf(FORM_FIELD),
                    options = groupOptions.map { SelectOption(it, it) }.toTypedArray()
                ),
                createSelectWithOptions(
                    clazz = stylesOf(FORM_FIELD),
                    options = roleOptions.map { SelectOption(it, it) }.toTypedArray()
                ),
                createWrappedInput(clazz = stylesOf(FORM_FIELD), type = "text", placeholder = "Attendoo Username"),
                createWrappedInput(clazz = stylesOf(FORM_FIELD), type = "password", placeholder = "Attendoo Password")
            )
        )
    }
    
    private fun createSubmitButton(): HTMLElement
    {
        val button = createButton(text = "Create", clazz = stylesOf(SUBMIT_BUTTON))
        button.appendChild(createSpan(text = "manufacturing", clazz = stylesOf(MATERIAL_SYMBOLS_OUTLINED, ICON)))
        return button
    }
}
