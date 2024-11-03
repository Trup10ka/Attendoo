package com.trup10ka.attendoo.exceptions

import java.io.File

class MissingImportantFileException(message: String, file: File) : RuntimeException(message)
{
    init
    {
        if (!file.exists())
        {
            file.createNewFile()
        }
    }
}
