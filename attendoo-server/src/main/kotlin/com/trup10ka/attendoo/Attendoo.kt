package com.trup10ka.attendoo

import com.trup10ka.attendoo.config.ConfigDistributor.config
import io.github.oshai.kotlinlogging.KotlinLogging
import io.ktor.server.application.Application
import io.ktor.server.engine.EmbeddedServer
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty

private val logger = KotlinLogging.logger {}

class Attendoo
{
    private lateinit var server: EmbeddedServer<*, *>

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
        setupEngine()
    }

    fun startApp()
    {
        server.start(wait = true)
    }
}
