package com.trup10ka.attendoo

import com.trup10ka.attendoo.config.Config
import com.trup10ka.attendoo.config.FileConfigProvider

class Attendoo
{
    private lateinit var config: Config

    private val configProvider = FileConfigProvider("application.conf", Attendoo::class.java.classLoader)

    fun loadConfig()
    {
        config = configProvider.getConfig()
    }

    fun startApp()
    {
        print(config)
    }
}
