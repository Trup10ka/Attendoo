package com.trup10ka.attendoo.fetch

import com.trup10ka.attendoo.CLIENT_HOST_VAL
import com.trup10ka.attendoo.TOKEN_NAME
import io.ktor.client.HttpClient
import io.ktor.client.engine.js.Js
import io.ktor.client.request.header
import io.ktor.client.request.request
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.http.contentType
import io.ktor.http.headers
import io.ktor.http.path
import io.ktor.util.reflect.typeInfo
import kotlinx.browser.window
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement

class KtorHttpClient : com.trup10ka.attendoo.fetch.HttpClient
{
    private val client = HttpClient(Js)

    override suspend fun getVia(path: String): HttpResponse
    {
        return client.request {
            url {
                host = CLIENT_HOST_VAL
                path(path)
            }

            headers {
                header(HttpHeaders.Authorization, "Bearer ${window.localStorage.getItem(TOKEN_NAME)}")
            }

            method = HttpMethod.Get
        }
    }

    override suspend fun postJSONVia(path: String, body: String): HttpResponse
    {
        return client.request {
            url {
                host = CLIENT_HOST_VAL
                path(path)
            }
            headers {
                header(HttpHeaders.Authorization, "Bearer ${window.localStorage.getItem(TOKEN_NAME)}")
            }

            method = HttpMethod.Post
            contentType(ContentType.Application.Json)

            setBody(
                body,
                typeInfo<JSON>()
            )
        }
    }

    override suspend fun postJSONViaUnauthorized(path: String, body: String): JsonElement
    {
        val response = client.request {
            url {
                host = CLIENT_HOST_VAL
                path(path)
            }

            method = HttpMethod.Post
            contentType(ContentType.Application.Json)
            setBody(
                body,
                typeInfo<JSON>()
            )
        }.bodyAsText()

        return Json.decodeFromString(response)
    }
}
