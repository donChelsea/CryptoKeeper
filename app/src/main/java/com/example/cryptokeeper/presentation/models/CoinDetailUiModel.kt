package com.example.cryptokeeper.presentation.models

data class CoinDetailUiModel(
    val description: String = "",
    val id: String = "",
    val isActive: Boolean = false,
    val logo: String = "",
    val name: String = "",
    val rank: Int = 0,
    val symbol: String = "",
    val tags: List<TagUiModel> = emptyList(),
    val team: List<TeamMemberUiModel> = emptyList(),
)