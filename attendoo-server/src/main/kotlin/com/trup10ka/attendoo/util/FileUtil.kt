package com.trup10ka.attendoo.util

import java.io.File

fun isExistingDirectory(file: File): Boolean = file.exists() && file.isDirectory

fun hasContent(file: File): Boolean = file.exists() && file.listFiles().isNotEmpty() && file.isDirectory
