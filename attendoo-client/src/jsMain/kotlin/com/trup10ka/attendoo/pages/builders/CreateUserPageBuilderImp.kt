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
                id = CREATE_USER_PANE,
                clazz = stylesOf(
                    INNER_CONTAINER,
                    CREATE_USER_CONTAINER
                ),
                children = arrayOf(
                    createHeader(text = "Create user", clazz = stylesOf(CONTAINER_HEADER)),
                    createDiv(id = "create-user-container-splitter", clazz = stylesOf(CREATE_USER_CONTAINER_SPLITTER),
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
                createWrappedInput(id = CREATE_USER_FIRST_NAME, clazz = stylesOf(FORM_FIELD), type = "text", placeholder = "First Name (exp. Julia)"),
                createWrappedInput(id = CREATE_USER_LAST_NAME, clazz = stylesOf(FORM_FIELD), type = "text", placeholder = "Last Name (exp. Crook)"),
                createWrappedInput(id = CREATE_USER_EMAIL, clazz = stylesOf(FORM_FIELD), type = "email", placeholder = "Email"),
                createWrappedInput(id = CREATE_USER_PHONE, clazz = stylesOf(FORM_FIELD), type = "tel", placeholder = "Tel")
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
                    id = CREATE_USER_DEPARTMENT,
                    clazz = stylesOf(FORM_FIELD),
                    options = groupOptions.map { SelectOption(it, it) }.toTypedArray()
                ),
                createSelectWithOptions(
                    id = CREATE_USER_ROLE,
                    clazz = stylesOf(FORM_FIELD),
                    options = roleOptions.map { SelectOption(it, it) }.toTypedArray()
                ),
                createWrappedInput(id = CREATE_USER_USERNAME, clazz = stylesOf(FORM_FIELD), type = "text", placeholder = "Attendoo Username"),
                createWrappedInput(id = CREATE_USER_PASSWORD, clazz = stylesOf(FORM_FIELD), type = "password", placeholder = "Attendoo Password")
            )
        )
    }

    private fun createSubmitButton(): HTMLElement
    {
        val button = createButton(id = CREATE_USER_SUBMIT, text = "Create", clazz = stylesOf(SUBMIT_BUTTON))
        button.appendChild(createSpan(text = "manufacturing", clazz = stylesOf(MATERIAL_SYMBOLS_OUTLINED, ICON)))
        return button
    }
}
