package com.trup10ka.attendoo.db.services

import com.trup10ka.attendoo.db.dao.Proposal
import com.trup10ka.attendoo.dto.ProposalDTO

interface ProposalService
{
    suspend fun createProposal(proposalDTO: ProposalDTO): Proposal
    suspend fun deleteProposal(attendooProposalId: Int)
    suspend fun getProposalByAttendooId(attendooProposalId: Int): ProposalDTO?
    suspend fun getProposalByProposalId(proposalId: Int): ProposalDTO?
    suspend fun getAllProposals(): List<ProposalDTO>
}
