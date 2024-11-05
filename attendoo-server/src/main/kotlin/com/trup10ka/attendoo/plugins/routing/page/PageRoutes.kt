package com.trup10ka.attendoo.plugins.routing.page

import com.trup10ka.attendoo.util.getFileOrThrow
import io.ktor.server.response.respondFile
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import java.io.File

fun Route.routePages()
{
    val index = getFileOrThrow("resources/index.html")

    routePage("/", index)
}

private fun Route.routePage(path: String, page: File) = get(path) { call.respondFile(page) }
