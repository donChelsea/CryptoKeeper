package com.example.cryptokeeper.data.remote.dtos

import com.example.cryptokeeper.domain.model.TeamMember

data class TeamMemberDto(
    val id: String,
    val name: String,
    val position: String
) {
    fun toDomain() = TeamMember(
        id = id,
        name = name,
        position = position
    )
}