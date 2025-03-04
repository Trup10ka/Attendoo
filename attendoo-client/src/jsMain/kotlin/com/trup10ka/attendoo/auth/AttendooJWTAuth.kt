package com.trup10ka.attendoo.auth

import com.trup10ka.attendoo.LOGIN_ENDPOINT
import com.trup10ka.attendoo.STATUS_NAME
import com.trup10ka.attendoo.TOKEN_NAME
import com.trup10ka.attendoo.fetch.HttpClient
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.headers
import kotlinx.browser.window
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.jsonObject

class AttendooJWTAuth(
    private val ktorClient: HttpClient
) : Authenticator
{
    private val token: String
        get() = window.localStorage.getItem(TOKEN_NAME) ?: ""
    
    override suspend fun login(): String
    {
        try
        {
            val response = ktorClient.postJSONViaUnauthorized(LOGIN_ENDPOINT, "") as JsonElement
            val token = response.jsonObject[TOKEN_NAME]
            val status = response.jsonObject[STATUS_NAME]
            if (token == null)
            {
                return status.toString()
            }
            setToken(token.toString())
            return "Success"
        }
        catch (e: Exception)
        {
            console.error("Error logging in: $e")
            return "Error logging in: $e"
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
    
    override suspend fun isAuthenticated(): Boolean
    {
        return false
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
    
    private fun setToken(token: String) = window.localStorage.setItem(TOKEN_NAME, token)
}
