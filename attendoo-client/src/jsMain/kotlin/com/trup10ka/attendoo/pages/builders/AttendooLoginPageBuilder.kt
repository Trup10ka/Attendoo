package com.trup10ka.attendoo.pages.builders

import com.trup10ka.attendoo.util.createSpan
import com.trup10ka.attendoo.pages.StyleClasses.*
import com.trup10ka.attendoo.util.createButton
import com.trup10ka.attendoo.util.createDiv
import com.trup10ka.attendoo.util.createForm
import com.trup10ka.attendoo.util.createHeader
import com.trup10ka.attendoo.util.createWrappedInput
import kotlinx.browser.document
import org.w3c.dom.HTMLElement

class AttendooLoginPageBuilder : AttendooPageBuilder
{
    override val currentlyActiveHTMLElements = mutableSetOf<HTMLElement>()
    private val loginFormBuilder = LoginFormBuilder()
    
    override fun buildDynamicElement(appender: HTMLElement?)
    {
        val loginPaneDiv = createDiv(clazz = LOGIN_PANE.toString())
        val registerSpan = createSpan(clazz = LOGIN_REGISTER_ACTION.toString(), text = "Register?")
        
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
    
    private class LoginFormBuilder : AttendooPageBuilder
    {
        override val currentlyActiveHTMLElements = mutableSetOf<HTMLElement>()
        
        override fun buildDynamicElement(appender: HTMLElement?)
        {
            appender?.let {
                val loginForm = createForm(clazz = LOGIN_FORM.toString(),
                    children = arrayOf(
                        createHeader(clazz = LOGIN_PANE_HEADER_TEXT.toString(), text = "Login"),
                        createWrappedInput(clazz = FORM_FIELD.toString(), type = "text", placeholder = "Email"),
                        createWrappedInput(clazz = FORM_FIELD.toString(), type = "password", placeholder = "Password"),
                        createButton(clazz = SUBMIT_BUTTON.toString(), text = "Login")
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
