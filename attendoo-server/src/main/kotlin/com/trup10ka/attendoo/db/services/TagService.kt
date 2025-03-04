package com.trup10ka.attendoo.db.services

import com.trup10ka.attendoo.db.dao.Tag

interface TagService
{
    suspend fun createTag(name: String)
    suspend fun deleteTag(name: String)
    suspend fun getTagByName(name: String): Tag?
    suspend fun getTagById(id: Int): Tag?
    suspend fun getAllTags(): List<Tag>
}
