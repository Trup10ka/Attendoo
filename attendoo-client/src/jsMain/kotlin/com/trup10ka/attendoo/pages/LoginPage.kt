package com.trup10ka.attendoo.pages

import com.trup10ka.attendoo.AttendooPageManager
import com.trup10ka.attendoo.auth.Authenticator
import com.trup10ka.attendoo.pages.builders.LoginPageBuilder
import com.trup10ka.attendoo.pages.constant.PageType
import com.trup10ka.attendoo.util.launchDefaultCoroutine
import com.trup10ka.attendoo.pages.constant.ElementID.*
import com.trup10ka.attendoo.util.getButtonByID
import com.trup10ka.attendoo.util.getInputByID
import kotlinx.browser.window

class LoginPage(
    override val pageType: PageType,
    override val pageManager: AttendooPageManager,
    private val jwtAuthenticator: Authenticator
) : Page
{
    override val pageBuilder = LoginPageBuilder()
    
    override fun init()
    {
        val loginButton = getButtonByID(LOGIN_SUBMIT_BUTTON)!!
        val usernameInput = getInputByID(USERNAME_INPUT_FIELD)!!
        val passwordInput = getInputByID(PASSWORD_INPUT_FIELD)!!
        
        loginButton.addEventListener("click",  { event ->
            event.preventDefault()
            
            val username = usernameInput.value.trim()
            val password = passwordInput.value.trim()
            
            if (username.isEmpty() || password.isEmpty())
            {
                window.alert("Username or password is empty")
                return@addEventListener
            }
            launchDefaultCoroutine {
                val isSuccessful = jwtAuthenticator.login(username, password)
                if (isSuccessful)
                {
                    pageManager.uriHandler.updateURI(PageType.DASHBOARD_PAGE.pageRoute)
                    pageManager.switchToPage(pageManager.getCurrentPage())
                }
                else
                {
                    window.alert("Login failed. Please check your credentials and try again.")
                }
            }
        })
    }
    
    override fun show()
    {
        pageBuilder.buildDynamicElement()
        init()
    }
    
    override fun hide()
    {
        pageBuilder.eraseDynamicElement()
    }
}
