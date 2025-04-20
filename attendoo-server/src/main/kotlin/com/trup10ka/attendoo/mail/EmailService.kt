package com.trup10ka.attendoo.mail

import com.trup10ka.attendoo.data.User
import com.trup10ka.attendoo.data.Request

/**
 * Interface for sending emails
 */
interface EmailService
{
    fun init()
    
    /**
     * Sends an email to the specified recipient
     *
     * @param to recipient email address
     * @param subject email subject
     * @param body email body (HTML)
     * @return true if the email was sent successfully, false otherwise
     */
    suspend fun sendEmail(to: String, subject: String, body: String): Boolean
    
    /**
     * Sends a welcome email to a newly created user
     *
     * @param user the newly created user
     * @return true if the email was sent successfully, false otherwise
     */
    suspend fun sendWelcomeEmail(user: User): Boolean
    
    /**
     * Sends a notification email when a new request is created
     *
     * @param request the newly created request
     * @return true if the email was sent successfully, false otherwise
     */
    suspend fun sendRequestCreatedEmail(request: Request): Boolean
}
