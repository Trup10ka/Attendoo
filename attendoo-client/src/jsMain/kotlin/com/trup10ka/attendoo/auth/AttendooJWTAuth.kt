package com.trup10ka.attendoo.auth

import com.trup10ka.attendoo.ERROR_JSON_FIELD_NAME
import com.trup10ka.attendoo.LOGIN_ENDPOINT
import com.trup10ka.attendoo.STATUS_NAME
import com.trup10ka.attendoo.TOKEN_NAME
import com.trup10ka.attendoo.VERIFY_ENDPOINT
import com.trup10ka.attendoo.fetch.HttpClient
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.headers
import io.ktor.client.statement.HttpResponse
import kotlinx.browser.window
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

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
            
            val response = ktorClient.postJSONViaUnauthorized(LOGIN_ENDPOINT, jsonBody) as JsonElement
            
            return handleTokenResponse(response)
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
    
    override suspend fun isAuthenticated(): Boolean
    {
        window.localStorage.getItem(TOKEN_NAME) ?: return false
        return try
        {
            val response = ktorClient.getVia(VERIFY_ENDPOINT) as HttpResponse
            return response.status.value == 200
        }
        catch (_: Exception)
        {
            false
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
    
    private fun handleTokenResponse(response: JsonElement): Boolean
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
        
        setToken(token.toString())
        return true
    }
    
    private fun setToken(token: String) = window.localStorage.setItem(TOKEN_NAME, token)
}
