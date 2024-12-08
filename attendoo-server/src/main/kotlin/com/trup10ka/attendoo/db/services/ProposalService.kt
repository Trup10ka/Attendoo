package com.trup10ka.attendoo.db.services

interface ProposalService
{
    fun createProposal()
    fun getProposal()
    fun updateProposal()
    fun deleteProposal()
    fun getProposalTags()
    fun getProposalByTag()
    fun getProposalByUser()
}
