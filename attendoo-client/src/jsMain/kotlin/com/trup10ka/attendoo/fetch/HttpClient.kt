package com.trup10ka.attendoo.fetch

interface HttpClient
{
    suspend fun getVia(path: String): Any
    suspend fun postJSONVia(path: String, body: String): Any
    suspend fun postJSONViaUnauthorized(path: String, body: String): Any
}
