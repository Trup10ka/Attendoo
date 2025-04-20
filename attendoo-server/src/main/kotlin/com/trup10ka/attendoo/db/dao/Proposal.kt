package com.trup10ka.attendoo.db.dao

import com.trup10ka.attendoo.db.tables.Proposals
import com.trup10ka.attendoo.dto.ProposalDTO
import com.trup10ka.attendoo.util.convertToKotlinxDateTime
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class Proposal(id: EntityID<Int>) : IntEntity(id)
{
    companion object : IntEntityClass<Proposal>(Proposals)

    var name by Proposals.name
    var description by Proposals.description
    var proposer by User referencedOn Proposals.proposerId
    var proposed by User referencedOn Proposals.proposedId
    var createdAt by Proposals.createdAt
    var resolvedAt by Proposals.resolvedAt
    var currentUserStatus by UserStatus referencedOn Proposals.currentUserStatus
    var proposedUserStatus by UserStatus referencedOn Proposals.proposedUserStatus
}
