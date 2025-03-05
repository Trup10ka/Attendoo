package com.trup10ka.attendoo.db.services

import com.trup10ka.attendoo.db.dao.Proposal
import com.trup10ka.attendoo.db.tables.Proposals
import com.trup10ka.attendoo.dto.ProposalDTO
import com.trup10ka.attendoo.util.convertToKotlinxLocalDateTime

class ProposalExposedService(
    private val userStatusService: UserStatusService
) : ProposalService
{
    override suspend fun createProposal(proposalDTO: ProposalDTO): Proposal
    {
        val currentUserStatusDao = userStatusService.getUserStatusByName(proposalDTO.currentStatus)!!
        
        var proposedUserStatusDao = userStatusService.getUserStatusByName(proposalDTO.proposedStatus)
        
        if (proposedUserStatusDao == null)
        {
            proposedUserStatusDao = userStatusService.createUserStatus(proposalDTO.proposedStatus)
        }
        
        return Proposal.new {
            attendooProposalId = proposalDTO.attendooProposalId
            name = proposalDTO.name
            description = proposalDTO.description
            createdAt = convertToKotlinxLocalDateTime(proposalDTO.createdAt)
            resolvedAt = proposalDTO.resolvedAt?.let { convertToKotlinxLocalDateTime(it) }
            currentUserStatus = currentUserStatusDao
            proposedUserStatus = proposedUserStatusDao
        }
    }
    
    override suspend fun deleteProposal(attendooProposalId: Int)
    {
        val proposalDao = getProposalByAttendooId(attendooProposalId)
        proposalDao?.delete()
    }
    
    override suspend fun getProposalByAttendooId(attendooProposalId: Int): Proposal?
    {
        return Proposal.find { Proposals.attendooProposalId eq attendooProposalId }.singleOrNull()
    }
    
    override suspend fun getProposalByProposalId(proposalId: Int): Proposal?
    {
        return Proposal.findById(proposalId)
    }
    
    override suspend fun getAllProposals(): List<Proposal>
    {
        return Proposal.all().toList()
    }
}
