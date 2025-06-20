package com.example.cryptokeeper.domain.model

import com.example.cryptokeeper.presentation.models.TagUiModel

data class Tag(
    val id: String,
    val name: String
) {
    fun toUiModel() = TagUiModel(
        id = id,
        name = name
    )
}
