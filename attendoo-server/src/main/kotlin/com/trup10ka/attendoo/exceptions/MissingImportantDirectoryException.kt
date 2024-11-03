package com.trup10ka.attendoo.exceptions

import java.io.File

class MissingImportantDirectoryException(message: String, file: File) : RuntimeException(message)
{
    init
    {
        if (!file.exists())
        {
            file.mkdir()
        }
    }
}
