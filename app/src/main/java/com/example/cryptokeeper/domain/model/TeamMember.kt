package com.example.cryptokeeper.domain.model

import com.example.cryptokeeper.presentation.models.TeamMemberUiModel

data class TeamMember(
    val id: String,
    val name: String,
    val position: String,
) {
    fun toUiModel() = TeamMemberUiModel(
        id = id,
        name = name,
        position = position,
    )
}