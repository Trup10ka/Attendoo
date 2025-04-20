package com.trup10ka.attendoo.db.services

import com.trup10ka.attendoo.db.dao.Proposal
import com.trup10ka.attendoo.dto.ProposalDTO

interface ProposalService
{
    suspend fun createProposal(proposalDTO: ProposalDTO): Proposal
    suspend fun deleteProposal(proposalId: Int)
    suspend fun getProposalByProposerId(proposerId: Int): List<Proposal>
    suspend fun getProposalByProposedId(proposedId: Int): List<Proposal>
    suspend fun getProposalByProposalId(proposalId: Int): Proposal?
    suspend fun getAllProposals(): List<Proposal>
}
