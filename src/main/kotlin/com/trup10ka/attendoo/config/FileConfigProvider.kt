package com.trup10ka.attendoo.config

import com.sksamuel.hoplite.ConfigLoaderBuilder
import com.sksamuel.hoplite.ExperimentalHoplite
import com.sksamuel.hoplite.addFileSource
import com.trup10ka.attendoo.util.copyResourceIfNotExists
import java.io.File

class FileConfigProvider(private val path: String, private val classLoader: ClassLoader) : ConfigProvider
{
    @OptIn(ExperimentalHoplite::class)
    override fun getConfig(): Config
    {
        val configFile = File(path)

        copyResourceIfNotExists(
            classLoader.getResourceAsStream(CONFIG_TEMPLATE_PATH) ?: throw IllegalStateException("Resource 'application.conf' not found."),
            configFile
        )

        return ConfigLoaderBuilder.default()
            .addFileSource(configFile)
            .withExplicitSealedTypes()
            .build()
            .loadConfigOrThrow<Config>()
    }

    companion object
    {
        private const val CONFIG_TEMPLATE_PATH = "application.conf"
    }
}
