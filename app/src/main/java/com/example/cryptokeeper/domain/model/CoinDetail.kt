package com.example.cryptokeeper.domain.model

import com.example.cryptokeeper.presentation.models.CoinDetailUiModel

data class CoinDetail(
    val description: String,
    val id: String,
    val isActive: Boolean,
    val logo: String,
    val name: String,
    val rank: Int,
    val symbol: String,
    val tags: List<Tag>,
    val team: List<TeamMember>,
) {
    fun toUiModel() = CoinDetailUiModel(
        description = description,
        id = id,
        isActive = isActive,
        logo = logo,
        name = name,
        rank = rank,
        symbol = symbol,
        tags = tags.map { it.toUiModel() },
        team = team.map { it.toUiModel() },
    )
}