package com.trup10ka.attendoo

import com.trup10ka.attendoo.config.Config
import com.trup10ka.attendoo.config.FileConfigProvider
import io.github.oshai.kotlinlogging.KotlinLogging
import io.ktor.server.application.Application
import io.ktor.server.engine.EmbeddedServer
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty

private val logger = KotlinLogging.logger {}

class Attendoo
{
    private lateinit var config: Config
    private lateinit var server: EmbeddedServer<*, *>

    private val configProvider = FileConfigProvider("application.conf", Attendoo::class.java.classLoader)

    private fun loadConfig()
    {
        config = configProvider.getConfig()

        logger.info { "Configuration loaded." }
    }

    private fun setupEngine()
    {
        server = embeddedServer(
            Netty,
            port = config.port,
            host = config.host,
            module = Application::attendooModule
        )

    }

    fun init()
    {
        loadConfig()
        setupEngine()
    }

    fun startApp()
    {
        server.start(wait = true)
    }
}
