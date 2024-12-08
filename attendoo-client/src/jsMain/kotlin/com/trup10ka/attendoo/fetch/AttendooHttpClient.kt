package com.trup10ka.attendoo.fetch

interface AttendooHttpClient
{
    suspend fun getVia(path: String): Any
    suspend fun postJSONVia(path: String, body: String): Any
}
