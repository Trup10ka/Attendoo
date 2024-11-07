package com.trup10ka.attendoo.db.tables

import org.jetbrains.exposed.dao.id.IntIdTable

object ProposalTags : IntIdTable("proposal_tag")
{
    val proposal = reference("proposal_id", Proposals)
    val tag = reference("tag_id", Tags)
}
