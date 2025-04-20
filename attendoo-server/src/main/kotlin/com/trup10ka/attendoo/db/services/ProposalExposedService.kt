package com.trup10ka.attendoo.db.services

import com.trup10ka.attendoo.db.dao.Proposal
import com.trup10ka.attendoo.db.tables.Proposals
import com.trup10ka.attendoo.dto.ProposalDTO
import com.trup10ka.attendoo.util.convertToJavaDateTime

class ProposalExposedService(
    private val userStatusService: UserStatusService,
    private val userService: UserService
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

        val proposerUser = userService.getUserById(proposalDTO.proposerId)
            ?: throw IllegalArgumentException("Proposer user not found")

        val proposedUser = userService.getUserById(proposalDTO.proposedId)
            ?: throw IllegalArgumentException("Proposed user not found")

        return Proposal.new {
            name = proposalDTO.name
            description = proposalDTO.description
            proposer = proposerUser
            proposed = proposedUser
            createdAt = proposalDTO.createdAt.convertToJavaDateTime()
            resolvedAt = proposalDTO.resolvedAt?.convertToJavaDateTime()
            currentUserStatus = currentUserStatusDao
            proposedUserStatus = proposedUserStatusDao
        }
    }

    override suspend fun deleteProposal(proposalId: Int)
    {
        val proposalDao = getProposalByProposalId(proposalId)
        proposalDao?.delete()
    }

    override suspend fun getProposalByProposerId(proposerId: Int): List<Proposal>
    {
        val proposerUser = userService.getUserById(proposerId)
            ?: return emptyList()

        return Proposal.find { Proposals.proposerId eq proposerUser.id }.toList()
    }

    override suspend fun getProposalByProposedId(proposedId: Int): List<Proposal>
    {
        val proposedUser = userService.getUserById(proposedId)
            ?: return emptyList()

        return Proposal.find { Proposals.proposedId eq proposedUser.id }.toList()
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
