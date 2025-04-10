package com.trup10ka.attendoo.pages

import com.trup10ka.attendoo.LOGIN_ENDPOINT
import com.trup10ka.attendoo.fetch.HttpClient
import com.trup10ka.attendoo.pages.builders.LoginPageBuilder
import com.trup10ka.attendoo.pages.constant.PageType
import com.trup10ka.attendoo.util.getDivByID
import com.trup10ka.attendoo.util.launchDefaultCoroutine
import com.trup10ka.attendoo.pages.constant.ElementID.*
import com.trup10ka.attendoo.util.getButtonByID
import com.trup10ka.attendoo.util.getInputByID
import io.ktor.client.call.body
import io.ktor.client.request.forms.FormDataContent
import io.ktor.client.statement.HttpResponse
import io.ktor.http.Parameters
import kotlinx.serialization.json.Json

class LoginPage(
    override val pageType: PageType,
    private val httpClient: HttpClient
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
                console.log("Username or password is empty")
                return@addEventListener
            }
            launchDefaultCoroutine {
                performLogin(username, password)
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
    
    private suspend fun performLogin(username: String, password: String)
    {
        val response = httpClient.postJSONViaUnauthorized(
            LOGIN_ENDPOINT,
            parseCredentialsToJSON(username, password)
        ) as? HttpResponse
        
        if (response == null)
        {
            console.log("Response is null")
            return
        }
        
        val responseText = response.body<String>()
        
        console.log(responseText)
    }
    
    private fun parseCredentialsToJSON(username: String, password: String): String
    {
        return Json.encodeToString(
            mapOf(
                "username" to username,
                "password" to password
            )
        )
    }
}
