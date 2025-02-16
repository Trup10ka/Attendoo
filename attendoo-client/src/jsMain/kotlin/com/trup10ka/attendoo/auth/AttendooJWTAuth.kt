package com.trup10ka.attendoo.auth

import com.trup10ka.attendoo.fetch.KtorHttpClient
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.headers
import kotlinx.browser.window

class AttendooJWTAuth(
    private val ktorHttpClient: KtorHttpClient
) : Authenticator
{
    override fun login()
    {
    }

    override fun logout()
    {
    }

    override fun register()
    {
    }

    override fun addAuthMethodToRequest(request: Any)
    {
        if (request !is HttpRequestBuilder)
        {
            throw IllegalArgumentException("Request must be of type HttpRequestBuilder in AttendooJWTAuth")
        }

        val token = getToken()
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

    private fun getToken() = window.localStorage.getItem("token") ?: ""

    private fun setToken(token: String) = window.localStorage.setItem("token", token)
}
