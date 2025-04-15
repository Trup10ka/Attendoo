package com.trup10ka.attendoo.pages.builders

import com.trup10ka.attendoo.pages.constant.StyleClass.*
import com.trup10ka.attendoo.pages.constant.ElementID.*
import com.trup10ka.attendoo.util.createButton
import com.trup10ka.attendoo.util.*
import kotlinx.browser.document
import org.w3c.dom.HTMLElement

class LoginPageBuilder : PageBuilder
{
    override val currentlyActiveHTMLElements = mutableSetOf<HTMLElement>()
    private val loginFormBuilder = LoginFormBuilder()
    
    override fun buildDynamicElement(appender: HTMLElement?)
    {
        val loginPaneDiv = createDiv(clazz = stylesOf(LOGIN_PANE))
        val registerSpan = createSpan(
            clazz = stylesOf(LOGIN_REGISTER_ACTION),
            text = "Register?"
        )
        
        loginFormBuilder.buildDynamicElement(loginPaneDiv)
        loginPaneDiv.appendChild(registerSpan)
        
        document.body?.appendChild(loginPaneDiv)
        currentlyActiveHTMLElements.add(loginPaneDiv)
    }
    
    override fun eraseDynamicElement()
    {
        loginFormBuilder.eraseDynamicElement()
        currentlyActiveHTMLElements.forEach {
            it.remove()
            currentlyActiveHTMLElements.remove(it)
        }
    }
    
    private class LoginFormBuilder : PageBuilder
    {
        override val currentlyActiveHTMLElements = mutableSetOf<HTMLElement>()
        
        override fun buildDynamicElement(appender: HTMLElement?)
        {
            appender?.let {
                val loginForm = createForm(clazz = stylesOf(LOGIN_FORM),
                    children = arrayOf(
                        createHeader(
                            clazz = stylesOf(LOGIN_PANE_HEADER_TEXT),
                            text = "Login"
                        ),
                        createWrappedInput(
                            id = USERNAME_INPUT_FIELD,
                            clazz = stylesOf(FORM_FIELD),
                            type = "text",
                            placeholder = "Username"
                        ),
                        createWrappedInput(
                            id = PASSWORD_INPUT_FIELD,
                            clazz = stylesOf(FORM_FIELD),
                            type = "password",
                            placeholder = "Password"
                        ),
                        createButton(
                            id = LOGIN_SUBMIT_BUTTON,
                            clazz = stylesOf(SUBMIT_BUTTON),
                            text = "Login",
                            type = "button"
                        )
                    )
                )
                currentlyActiveHTMLElements.add(loginForm)
                appender.appendChild(loginForm)
            }
        }
        
        override fun eraseDynamicElement()
        {
            currentlyActiveHTMLElements.forEach {
                it.remove()
                currentlyActiveHTMLElements.remove(it)
            }
        }
    }
}
