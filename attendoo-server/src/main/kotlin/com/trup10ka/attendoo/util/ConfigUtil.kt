package com.trup10ka.attendoo.util

import io.github.oshai.kotlinlogging.KotlinLogging
import java.io.File
import java.io.InputStream
import java.nio.file.Files

private val logger = KotlinLogging.logger {}

/**
 * Copies a [resource][InputStream] to a file system if the destination file doesn't exist already.
 * @return Whether the file was copied or not.
 */
fun copyResourceIfNotExists(resource: InputStream, destinationFile: File): Boolean
{
    if (destinationFile.exists())
        return false

    logger.warn { "Configuration file is missing, creating default configuration in 'application.conf'" }
    return Files.copy(resource, destinationFile.toPath()) != 0L
}
