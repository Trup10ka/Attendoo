package com.trup10ka.attendoo.pages

import com.trup10ka.attendoo.AttendooClient
import com.trup10ka.attendoo.AttendooPageManager
import com.trup10ka.attendoo.TOKEN_NAME
import com.trup10ka.attendoo.auth.AuthenticationStatus
import com.trup10ka.attendoo.auth.Authenticator
import com.trup10ka.attendoo.pages.builders.LoginPageBuilder
import com.trup10ka.attendoo.pages.constant.PageType
import com.trup10ka.attendoo.util.launchDefaultCoroutine
import com.trup10ka.attendoo.pages.constant.ElementID.*
import com.trup10ka.attendoo.util.getButtonByID
import com.trup10ka.attendoo.util.getDivByID
import com.trup10ka.attendoo.util.getInputByID
import kotlinx.browser.window

class LoginPage(
    override val pageType: PageType,
    override val pageManager: AttendooPageManager,
    private val jwtAuthenticator: Authenticator,
    private val attendooClient: AttendooClient
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
                jwtAuthenticator.login(username, password)
                
                val isSuccessful = jwtAuthenticator.isAuthenticated()
                
                if (isSuccessful == AuthenticationStatus.NOT_AUTHENTICATED)
                {
                    window.alert("Login failed. Please check your credentials and try again.")
                    return@launchDefaultCoroutine
                }
                attendooClient.createAndInitButtons(isSuccessful)
                pageManager.uriHandler.updateURI(PageType.DASHBOARD_PAGE.pageRoute)
                pageManager.switchToPage(pageManager.getCurrentPage())
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
