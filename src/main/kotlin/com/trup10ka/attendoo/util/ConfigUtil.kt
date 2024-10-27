package com.trup10ka.attendoo.util

import java.io.File
import java.io.InputStream
import java.nio.file.Files

/**
 * Copies a [resource][InputStream] to a file system if the destination file doesn't exist already.
 * @return Whether the file was copied or not.
 */
fun copyResourceIfNotExists(resource: InputStream, destinationFile: File): Boolean
{
    if (destinationFile.exists())
        return false

    return Files.copy(resource, destinationFile.toPath()) != 0L
}
