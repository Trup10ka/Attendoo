package com.trup10ka.attendoo.db.tables

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.javatime.datetime

object Proposals : IntIdTable("proposal")
{
    val name = varchar("name", 255)
    val description = text("description")
    val attendooProposalId = integer("attendoo_proposal_id")
    val createdAt = datetime("created_at")
    val resolvedAt = datetime("resolved_at").nullable()
    val currentEmployeeStatus = reference("current_employee_status_id", UserStatuses)
    val proposedEmployeeStatus = reference("proposed_employee_status_id", UserStatuses)
}
