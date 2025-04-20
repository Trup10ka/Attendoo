package com.trup10ka.attendoo.mail

import com.trup10ka.attendoo.config.Config
import com.trup10ka.attendoo.config.ConfigDistributor
import com.trup10ka.attendoo.data.User
import com.trup10ka.attendoo.data.Request
import io.github.oshai.kotlinlogging.KotlinLogging
import jakarta.mail.Authenticator
import jakarta.mail.Message
import jakarta.mail.PasswordAuthentication
import jakarta.mail.Session
import jakarta.mail.Transport
import jakarta.mail.internet.InternetAddress
import jakarta.mail.internet.MimeMessage
import java.util.Properties

private val logger = KotlinLogging.logger {}

class SimpleEmailService() : EmailService
{
    
    private lateinit var hostMail: String
    
    private lateinit var emailServiceProperties: Properties
    
    private lateinit var emailSession: Session
    
    override fun init()
    {
        loadHostMail()
        setupProperties()
        setupSession()
    }
    
    override suspend fun sendEmail(to: String, subject: String, body: String): Boolean
    {
        val message = MimeMessage(emailSession).apply {
            setFrom(InternetAddress(hostMail))
            setRecipients(Message.RecipientType.TO, InternetAddress.parse(to))
            setSubject(subject)
            setContent(body, "text/html; charset=utf-8")
        }
        
        Transport.send(message)
        return true
    }
    
    override suspend fun sendWelcomeEmail(user: User): Boolean
    {
        val subject = "Welcome to Attendoo!"
        val body = """
            <html>
                <body>
                    <h1>Welcome to Attendoo, ${user.firstName}!</h1>
                    <p>Your account has been created successfully.</p>
                    <p>Here are your account details:</p>
                    <ul>
                        <li><strong>Username:</strong> ${user.attendooUsername}</li>
                        <li><strong>Department:</strong> ${user.userDepartment}</li>
                        <li><strong>Role:</strong> ${user.role}</li>
                    </ul>
                    <p>You can now log in to the Attendoo application.</p>
                    <p>Best regards,<br>The Attendoo Team</p>
                </body>
            </html>
        """.trimIndent()
        
        return sendEmail(user.email, subject, body)
    }
    
    override suspend fun sendRequestCreatedEmail(request: Request): Boolean
    {
        val subject = "New Request Created"
        val body = """
            <html>
                <body>
                    <h1>New Request Created</h1>
                    <p>A new request has been created by ${request.user.firstName} ${request.user.lastName}.</p>
                    <p>Request details:</p>
                    <ul>
                        <li><strong>Company:</strong> ${request.company}</li>
                        <li><strong>Note:</strong> ${request.note}</li>
                        <li><strong>Status:</strong> ${request.status}</li>
                    </ul>
                    <p>Best regards,<br>The Attendoo Team</p>
                </body>
            </html>
        """.trimIndent()
        
        return sendEmail(request.user.email, subject, body)
    }
    
    private fun loadHostMail()
    {
        hostMail = ConfigDistributor.config.email.fromEmail
        if (hostMail.isEmpty())
        {
            logger.error { "Host mail is empty" }
            throw IllegalStateException("Host mail is empty")
        }
        
        logger.info { "Host mail: $hostMail" }
    }
    
    private fun setupSession()
    {
        emailSession = Session.getInstance(emailServiceProperties, object : Authenticator()
        {
            override fun getPasswordAuthentication(): PasswordAuthentication
            {
                return PasswordAuthentication(
                    ConfigDistributor.config.email.username,
                    ConfigDistributor.config.email.password
                )
            }
        })
    }
    
    private fun setupProperties()
    {
        emailServiceProperties = Properties().apply {
            put("mail.smtp.auth", "true")
            put("mail.smtp.starttls.enable", "true")
            put("mail.smtp.host", ConfigDistributor.config.email.host)
            put("mail.smtp.port", ConfigDistributor.config.email.port)
        }
    }
}
