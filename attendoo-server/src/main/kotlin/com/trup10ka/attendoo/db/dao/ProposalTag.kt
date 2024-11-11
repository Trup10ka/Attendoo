package com.trup10ka.attendoo.db.dao

import com.trup10ka.attendoo.db.tables.ProposalTags
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class ProposalTag(id: EntityID<Int>) : IntEntity(id)
{
    companion object : IntEntityClass<ProposalTag>(ProposalTags)

    var tagId by Tag referencedOn ProposalTags.tag
    var proposalId by Proposal referencedOn ProposalTags.proposal
}
