package com.trup10ka.attendoo.db.tables

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.javatime.datetime

object Proposals : IntIdTable("proposal")
{
    val name = varchar("name", 255)
    val description = text("description")
    val proposerId = reference("proposer_id", Users)
    val proposedId = reference("proposed_id", Users)
    val createdAt = datetime("created_at")
    val resolvedAt = datetime("resolved_at").nullable()
    val currentUserStatus = reference("current_user_status_id", UserStatuses)
    val proposedUserStatus = reference("proposed_user_status_id", UserStatuses)
}
