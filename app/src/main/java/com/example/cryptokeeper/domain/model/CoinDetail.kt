package com.example.cryptokeeper.domain.model

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
)

data class TeamMember(
    val id: String,
    val name: String,
    val position: String,
)

data class Tag(
    val id: String,
    val name: String
)
