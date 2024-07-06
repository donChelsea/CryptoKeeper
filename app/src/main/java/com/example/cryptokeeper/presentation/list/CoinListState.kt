package com.example.cryptokeeper.presentation.list

import com.example.cryptokeeper.domain.model.Coin

data class CoinListState(
    val screenData: ScreenData = ScreenData.Initial
)

sealed class ScreenData {
    data object Initial: ScreenData()
    data object Loading: ScreenData()
    data object Offline: ScreenData()
    data class Error(
        val message: String
    ): ScreenData()
    data class Data(
        val coins: List<Coin> = emptyList()
    ) : ScreenData()
}