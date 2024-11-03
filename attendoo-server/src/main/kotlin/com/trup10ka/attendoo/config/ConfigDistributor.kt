package com.trup10ka.attendoo.config

object ConfigDistributor
{
    private val configProvider = FileConfigProvider("application.conf", ConfigDistributor::class.java.classLoader)

    val config: Config by lazy { configProvider.getConfig() }
}
