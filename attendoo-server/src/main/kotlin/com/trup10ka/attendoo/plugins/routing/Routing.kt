package com.trup10ka.attendoo.plugins.routing

import com.trup10ka.attendoo.exceptions.MissingImportantDirectoryException
import com.trup10ka.attendoo.plugins.routing.api.routeAPICalls
import com.trup10ka.attendoo.plugins.routing.page.routePages
import com.trup10ka.attendoo.util.hasContent
import com.trup10ka.attendoo.util.isExistingDirectory
import io.github.oshai.kotlinlogging.KotlinLogging
import io.ktor.server.application.*
import io.ktor.server.http.content.staticFiles
import io.ktor.server.routing.*
import java.io.File

val logger = KotlinLogging.logger {}

fun Application.configureRouting()
{
    routing {

        routePages()
        routeAPICalls()
        routeStaticFiles()
    }
}

private fun Route.routeStaticFiles()
{
    val resourcesDir = File("resources")
    val publicDir = File("resources/public")

    checkForImportantDirectories(resourcesDir, publicDir)
    checkForPublicContent(publicDir)

    staticFiles("/public/", publicDir)
}

private fun checkForPublicContent(publicDir: File)
{
    if (!hasContent(publicDir))
    {
        logger.warn { "Public directory is empty. Consider adding scripts and styles" }
    }
}

private fun checkForImportantDirectories(resourcesDir: File, publicDir: File)
{
    if (!isExistingDirectory(resourcesDir))
    {
        throw MissingImportantDirectoryException("Root resources directory is missing. Resources directory was added, add content", resourcesDir)
    }
    else if (!isExistingDirectory(publicDir))
    {
        throw MissingImportantDirectoryException("Public directory is missing. Public directory was added.", publicDir)
    }
}
