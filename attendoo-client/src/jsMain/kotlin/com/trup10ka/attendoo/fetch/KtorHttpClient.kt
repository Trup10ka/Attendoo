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

class KtorHttpClient : com.trup10ka.attendoo.fetch.HttpClient
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
                append("Authorization", "Bearer ${window.localStorage.getItem("ATTENDOO_TOKEN")}")
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
            headers {
                append("Authorization", "Bearer ${window.localStorage.getItem("ATTENDOO_TOKEN")}")
            }
            
            method = HttpMethod.Post
            
            setBody(
                body,
                typeInfo<JSON>()
            )
        }
    }
    
    override suspend fun postJSONViaUnauthorized(path: String, body: String): Any
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
