package com.trup10ka.attendoo

import com.trup10ka.attendoo.db.client.ExposedDbClient
import com.trup10ka.attendoo.plugins.configureDB
import com.trup10ka.attendoo.plugins.http.configureHTTP
import com.trup10ka.attendoo.plugins.routing.configureRouting
import com.trup10ka.attendoo.plugins.configureSerialization
import com.trup10ka.attendoo.plugins.configureSockets
import com.trup10ka.attendoo.plugins.configureStatusPage
import com.trup10ka.attendoo.plugins.security.configureSecurity
import com.trup10ka.attendoo.mail.EmailService
import com.trup10ka.attendoo.mail.SimpleEmailService
import io.ktor.server.application.Application

fun Application.attendooModule()
{
    val dbClient = ExposedDbClient()
    val emailService: EmailService = SimpleEmailService()
    emailService.init()
    configureDB(dbClient)
    
    configureSecurity()
    configureHTTP()
    configureSerialization()
    configureSockets()
    configureStatusPage()
    configureRouting(dbClient, emailService)
}
