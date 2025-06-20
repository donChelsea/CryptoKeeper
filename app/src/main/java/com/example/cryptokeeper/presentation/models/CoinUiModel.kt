package com.example.cryptokeeper.presentation.models

data class CoinUiModel(
    val id: String,
    val isActive: Boolean,
    val name: String,
    val rank: Int,
    val symbol: String,
    val type: String
)