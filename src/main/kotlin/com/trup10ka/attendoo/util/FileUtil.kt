package com.trup10ka.attendoo.util

import java.io.File

fun isExistingDirectory(file: File): Boolean = file.exists() && file.isDirectory
