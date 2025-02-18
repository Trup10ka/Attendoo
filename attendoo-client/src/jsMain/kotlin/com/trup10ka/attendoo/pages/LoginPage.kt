package com.trup10ka.attendoo.pages

import com.trup10ka.attendoo.fetch.HttpClient
import com.trup10ka.attendoo.pages.constant.PageType
import com.trup10ka.attendoo.util.launchDefaultCoroutine
import io.ktor.client.call.body
import io.ktor.client.request.forms.FormDataContent
import io.ktor.client.statement.HttpResponse
import io.ktor.http.Parameters

class LoginPage(
    override val pageType: PageType,
    private val httpClient: HttpClient
) : Page
{
    override fun init()
    {
    }

    override fun show()
    {
    }

    override fun hide()
    {
    }

    private fun performLogin(username: String, password: String)
    {
        launchDefaultCoroutine {
            val response = httpClient.postJSONViaUnauthorized(
                "login",
                parseCredentialsToJSON(username, password)
            ) as? HttpResponse

            if (response == null)
            {
                console.log("Response is null")
                return@launchDefaultCoroutine
            }

            val responseText = response.body<String>()

            console.log(responseText)
        }

    }

    private fun parseCredentialsToJSON(username: String, password: String): String
    {
        return JSON.stringify(
            FormDataContent(
                Parameters.build {
                    append("username", username)
                    append("password", password)
                }
            )
        )
    }
}
