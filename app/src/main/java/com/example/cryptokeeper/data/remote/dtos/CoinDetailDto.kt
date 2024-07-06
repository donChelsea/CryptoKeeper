package com.example.cryptokeeper.data.remote.dtos

import com.example.cryptokeeper.domain.model.CoinDetail
import com.example.cryptokeeper.domain.model.Tag
import com.example.cryptokeeper.domain.model.TeamMember
import com.google.gson.annotations.SerializedName

data class CoinDetailDto(
    val description: String,
    val id: String,
    @SerializedName("is_active")
    val isActive: Boolean,
    val logo: String,
    val name: String,
    val rank: Int,
    val symbol: String,
    val tags: List<TagDto>,
    val team: List<TeamMemberDto>,
)

data class TeamMemberDto(
    val id: String,
    val name: String,
    val position: String
)

data class TagDto(
    val id: String,
    val name: String
)

fun CoinDetailDto.toCoinDetail() = CoinDetail(
    description = description,
    id = id,
    isActive = isActive,
    logo = logo,
    name = name,
    rank = rank,
    symbol = symbol,
    tags = tags.map { it.toTag() },
    team = team.map { it.toTeamMember() }
)

fun TeamMemberDto.toTeamMember() = TeamMember(
    id = id,
    name = name,
    position = position
)

fun TagDto.toTag() = Tag(
    id = id,
    name = name
)