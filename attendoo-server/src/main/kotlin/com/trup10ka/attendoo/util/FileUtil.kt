package com.trup10ka.attendoo.util

import java.io.File

fun isExistingDirectory(file: File): Boolean = file.exists() && file.isDirectory

fun hasContent(file: File): Boolean = file.exists() && file.listFiles().isNotEmpty() && file.isDirectory

fun getFileOrThrow(path: String): File
{
    val file = File(path)

    if (!file.exists())
    {
        throw IllegalArgumentException("File '${file.name}' does not exist on path: ${file.absolutePath}")
    }
    return file
}
