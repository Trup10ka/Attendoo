package com.trup10ka.attendoo.auth

import com.trup10ka.attendoo.ERROR_JSON_FIELD_NAME
import com.trup10ka.attendoo.FULL_LOGIN_ENDPOINT
import com.trup10ka.attendoo.FULL_VERIFY_ENDPOINT
import com.trup10ka.attendoo.STATUS_NAME
import com.trup10ka.attendoo.TOKEN_NAME
import com.trup10ka.attendoo.fetch.HttpClient
import com.trup10ka.attendoo.pages.constant.ElementID
import com.trup10ka.attendoo.util.getDivByID
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.headers
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import kotlinx.browser.window
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import org.w3c.dom.HTMLDivElement

class AttendooJWTAuth(
    private val ktorClient: HttpClient
) : Authenticator
{
    private val token: String
        get() = window.localStorage.getItem(TOKEN_NAME) ?: ""
    
    override suspend fun login(username: String, password: String): Boolean
    {
        try
        {
            val jsonBody = encodeJsonBody(username, password)
            
            val response = ktorClient.postJSONViaUnauthorized(FULL_LOGIN_ENDPOINT, jsonBody) as JsonElement
            
            return handleTokenResponse(response, username)
        }
        catch (e: Exception)
        {
            console.error("Error logging in: $e")
            return false
        }
    }
    
    override suspend fun logout(): String
    {
        return "Success"
    }
    
    override suspend fun register(): String
    {
        return "Success"
    }
    
    override suspend fun isAuthenticated(): AuthenticationStatus
    {
        window.localStorage.getItem(TOKEN_NAME) ?: return AuthenticationStatus.NOT_AUTHENTICATED
        return try
        {
            val response = ktorClient.getVia(FULL_VERIFY_ENDPOINT) as HttpResponse
            if (response.status.value == 200)
            {
                try
                {
                    val responseBody = response.bodyAsText()
                    val jsonElement = Json.parseToJsonElement(responseBody)
                    val role = jsonElement.jsonObject["role"]?.jsonPrimitive?.content
                    
                    if (role?.equals("admin", ignoreCase = true) == true)
                    {
                        AuthenticationStatus.AUTHENTICATED_ADMIN
                    }
                    else
                    {
                        AuthenticationStatus.AUTHENTICATED
                    }
                }
                catch (_: Exception)
                {
                    AuthenticationStatus.AUTHENTICATED
                }
            }
            else
            {
                AuthenticationStatus.NOT_AUTHENTICATED
            }
        }
        catch (_: Exception)
        {
            AuthenticationStatus.NOT_AUTHENTICATED
        }
    }
    
    override fun addAuthMethodToRequest(request: Any)
    {
        if (request !is HttpRequestBuilder)
        {
            throw IllegalArgumentException("Request must be of type HttpRequestBuilder in AttendooJWTAuth")
        }
        
        if (token.isEmpty())
        {
            request.headers {
                append("Authorization", "Bearer $token")
            }
        }
        else
        {
            request.headers {
                append("Authorization", "Bearer $token")
            }
        }
    }
    
    private fun encodeJsonBody(username: String, password: String): String
    {
        val body = mapOf(
            "username" to username,
            "password" to password
        )
        return Json.encodeToString(body)
    }
    
    private fun handleTokenResponse(response: JsonElement, username: String): Boolean
    {
        val keys = response.jsonObject.keys
        if (keys.contains(ERROR_JSON_FIELD_NAME))
        {
            val error = response.jsonObject[ERROR_JSON_FIELD_NAME]?.jsonPrimitive?.content
            window.alert("Error logging in: $error")
            return false
        }
        
        val token = response.jsonObject[TOKEN_NAME]?.jsonPrimitive?.content
        val status = response.jsonObject[STATUS_NAME]?.jsonPrimitive?.content
        
        if (token == null || status == null)
            return false
        
        setToken(token)
        setupUserNameAtTopBar(username)
        return true
    }
    
    // TODO: Style in CSS
    private fun setupUserNameAtTopBar(username: String)
    {
        val loggedInUser = getDivByID(ElementID.LOGGED_IN_USER)!!
        
        refreshStyleAndTextContent(loggedInUser, username)
        loggedInUser.addEventListener("click", {
            window.localStorage.removeItem(TOKEN_NAME)
            window.location.href = "/login"
        })
    }
    
    private fun refreshStyleAndTextContent(loggedInDiv: HTMLDivElement, username: String)
    {
        loggedInDiv.textContent = ""
        loggedInDiv.style.removeProperty("color")
        loggedInDiv.style.removeProperty("font-size")
        loggedInDiv.style.removeProperty("font-weight")
        loggedInDiv.style.removeProperty("cursor")
        loggedInDiv.style.removeProperty("margin-left")
        
        loggedInDiv.textContent = "Logged in as: $username"
        loggedInDiv.style.color = "orange"
        loggedInDiv.style.fontSize = "18px"
        loggedInDiv.style.fontWeight = "bold"
        loggedInDiv.style.cursor = "pointer"
        loggedInDiv.style.marginLeft = "10px"
    }
    
    private fun setToken(token: String) = window.localStorage.setItem(TOKEN_NAME, token)
}
