package com.trup10ka.attendoo.util

import com.trup10ka.attendoo.exceptions.MissingImportantFileException
import java.io.File

fun isExistingDirectory(file: File): Boolean = file.exists() && file.isDirectory

fun hasContent(file: File): Boolean = file.exists() && file.listFiles().isNotEmpty() && file.isDirectory

fun getFileOrThrow(path: String): File
{
    val file = File(path)

    if (!file.exists())
    {
        throw MissingImportantFileException("File '${file.name}' does not exist on path: ${file.absolutePath}. CREATED EMPTY 'index.html' FILE", file)
    }
    return file
}
