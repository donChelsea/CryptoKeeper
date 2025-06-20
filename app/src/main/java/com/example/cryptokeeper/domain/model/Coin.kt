package com.example.cryptokeeper.domain.model

import com.example.cryptokeeper.presentation.models.CoinUiModel

data class Coin(
    val id: String,
    val isActive: Boolean,
    val name: String,
    val rank: Int,
    val symbol: String,
    val type: String
) {
    fun toUiModel() = CoinUiModel(
        id = id,
        isActive = isActive,
        name = name,
        rank = rank,
        symbol = symbol,
        type = type,
    )
}