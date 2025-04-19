package com.trup10ka.attendoo.dto

import kotlinx.serialization.Serializable

@Serializable
data class DepartmentDTO(
    val id: Int,
    val name: String
)
