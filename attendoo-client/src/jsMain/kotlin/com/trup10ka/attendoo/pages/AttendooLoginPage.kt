package com.trup10ka.attendoo.pages

import com.trup10ka.attendoo.fetch.AttendooKtorHttpClient
import com.trup10ka.attendoo.util.launchCoroutine
import io.ktor.client.call.body
import io.ktor.client.request.forms.FormDataContent
import io.ktor.http.Parameters


class AttendooLoginPage(
    override val pageType: PageType,
    private val ktorClient: AttendooKtorHttpClient
) : AttendooPage
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
        launchCoroutine {
            val response = ktorClient.postJSONVia(
                "login",
                parseCredentialsToJSON(username, password)
            )
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
