package com.trup10ka.attendoo.db.dao

import com.trup10ka.attendoo.db.tables.Proposals
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class Proposal(id: EntityID<Int>) : IntEntity(id)
{
    companion object : IntEntityClass<Proposal>(Proposals)

    var name by Proposals.name
    var description by Proposals.description
    var attendooProposalId by Proposals.attendooProposalId
    var createdAt by Proposals.createdAt
    var resolvedAt by Proposals.resolvedAt
    var currentEmployeeStatus by EmployeeStatus referencedOn Proposals.currentEmployeeStatus
    var proposedEmployeeStatus by EmployeeStatus referencedOn Proposals.proposedEmployeeStatus
}
