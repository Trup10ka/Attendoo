package com.trup10ka.attendoo.fetch

import io.ktor.client.HttpClient
import io.ktor.client.engine.js.Js
import io.ktor.client.request.request
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpMethod
import io.ktor.http.headers
import io.ktor.http.path
import io.ktor.util.reflect.typeInfo
import kotlinx.browser.window

class AttendooKtorHttpClient : AttendooHttpClient
{
    private val client = HttpClient(Js)

    override suspend fun getVia(path: String): HttpResponse
    {
        return client.request {
            url {
                host = "localhost"
                path(path)
            }
            headers {
                append("Authorization", "Bearer ${window.localStorage.getItem("token")}")
            }
            method = HttpMethod.Get
        }
    }

    override suspend fun postJSONVia(path: String, body: String): HttpResponse
    {
        return client.request {
            url {
                host = "localhost"
                path(path)
            }
            method = HttpMethod.Post
            setBody(
                body,
                typeInfo<JSON>()
            )
        }
    }

}
