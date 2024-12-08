package com.trup10ka.attendoo.db.services

interface TagService
{
    fun createTag(name: String)
    fun deleteTag(name: String)
    fun getTag(name: String)
    fun getTags()
}
