package com.example.cryptokeeper.data.remote.dtos

import com.example.cryptokeeper.domain.model.Tag

data class TagDto(
    val id: String,
    val name: String
) {
    fun toDomain() = Tag(
        id = id,
        name = name
    )
}