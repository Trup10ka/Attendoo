package com.trup10ka.attendoo.db.services

import com.trup10ka.attendoo.db.dao.Tag
import com.trup10ka.attendoo.db.tables.Tags

class TagExposedService : TagService
{
    override suspend fun createTag(name: String)
    {
        Tag.new {
            this.name = name
        }
    }
    
    override suspend fun deleteTag(name: String)
    {
        val tag = getTagByName(name)
        tag?.delete()
    }
    
    override suspend fun getTagByName(name: String): Tag?
    {
        return Tag.find { Tags.name eq name }.singleOrNull()
    }
    
    override suspend fun getTagById(id: Int): Tag?
    {
        return Tag.findById(id)
    }
    
    override suspend fun getAllTags(): List<Tag>
    {
        return Tag.all().toList()
    }
}
