package com.trup10ka.attendoo.db.services

import com.trup10ka.attendoo.db.dao.Proposal
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
        TODO("Not yet implemented")
    }
    
    override suspend fun getProposalByAttendooId(attendooProposalId: Int): ProposalDTO?
    {
        TODO("Not yet implemented")
    }
    
    override suspend fun getProposalByProposalId(proposalId: Int): ProposalDTO?
    {
        TODO("Not yet implemented")
    }
    
    override suspend fun getAllProposals(): List<ProposalDTO>
    {
        TODO("Not yet implemented")
    }
}
