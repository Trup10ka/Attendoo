package com.trup10ka.attendoo.pages.builders

import com.trup10ka.attendoo.data.User
import com.trup10ka.attendoo.util.stylesOf
import com.trup10ka.attendoo.util.createDiv
import com.trup10ka.attendoo.pages.constant.StyleClass.*
import com.trup10ka.attendoo.pages.constant.ElementID.MAIN_USERS_CONTAINER
import com.trup10ka.attendoo.util.createSpan
import kotlinx.browser.window
import org.w3c.dom.HTMLElement

class UsersPageBuilderImp() : UsersPageBuilder
{
    override val currentlyActiveHTMLElements = mutableSetOf<HTMLElement>()
    
    private var usersContainer: HTMLElement? = null
    
    override fun buildDynamicElement(appender: HTMLElement?)
    {
        val usersContainer = createDiv(
            id = MAIN_USERS_CONTAINER,
            clazz = stylesOf(INNER_CONTAINER, USERS_CONTAINER)
        )
        
        appender!!.appendChild(usersContainer)
        currentlyActiveHTMLElements.add(usersContainer)
        this.usersContainer = usersContainer
    }
    
    override fun buildUserContainer(user: User)
    {
        if (usersContainer == null)
        {
            window.alert("Container not found, reload the page please")
            return
        }
        
        val userDiv = createDiv(
            id = user.attendooUsername,
            clazz = stylesOf(CONTAINER_TAB, USER),
            children = arrayOf(
                createUserCredentialsContainer(user),
                createUserInfoContainer(user),
                createActionBar(user)
            )
        )
        
        usersContainer!!.appendChild(userDiv)
        currentlyActiveHTMLElements.add(userDiv)
    }
    
    override fun eraseDynamicElement()
    {
        currentlyActiveHTMLElements.forEach { it.remove() }
        currentlyActiveHTMLElements.clear()
        usersContainer = null
    }
    
    private fun createUserCredentialsContainer(user: User): HTMLElement
    {
        return createDiv(
            id = "${user.attendooUsername}-credentials",
            clazz = stylesOf(MINI_CONTAINER_HEADER, USER_CREDENTIALS),
            children = arrayOf(
                
                createDiv(
                    clazz = stylesOf(ONE_LINE_CONTAINER, EMPLOYEE_NAME_CONTAINER)
                    
                ).apply { innerText = "${user.firstName} ${user.lastName}" },
                
                createDiv(
                    clazz = stylesOf(AVATAR),
                )
            )
        )
    }
    
    private fun createUserInfoContainer(user: User): HTMLElement
    {
        return createDiv(
            id = "${user.attendooUsername}-info",
            clazz = stylesOf(USER_INFO),
            children = arrayOf(
                
                createDiv(
                    clazz = stylesOf(ONE_LINE_CONTAINER)
                ).apply { innerText = "Tel: ${user.phoneNumber}" },
                
                createDiv(
                    clazz = stylesOf(ONE_LINE_CONTAINER)
                ).apply { innerText = "Email: ${user.email}" },
                
                createDiv(
                    clazz = stylesOf(ONE_LINE_CONTAINER),
                ).apply { innerText = "Role: ${user.role}" }
            )
        )
    }
    
    private fun createActionBar(user: User): HTMLElement
    {
        return createDiv(
            id = "${user.attendooUsername}-actions",
            clazz = stylesOf(ACTIONS_CONTAINER),
            children = arrayOf(
                createSpan(
                    clazz = stylesOf(MATERIAL_SYMBOLS_OUTLINED, ICON),
                    text = "open_in_full"
                ),
                createSpan(
                    clazz = stylesOf(MATERIAL_SYMBOLS_OUTLINED, ICON),
                    text = "edit"
                ),
                createSpan(
                    clazz = stylesOf(MATERIAL_SYMBOLS_OUTLINED, ICON),
                    text = "delete"
                )
            )
        )
    }
}
