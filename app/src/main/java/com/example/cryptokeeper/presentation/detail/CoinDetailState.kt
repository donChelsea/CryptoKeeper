package com.example.cryptokeeper.presentation.detail

import com.example.cryptokeeper.domain.model.CoinDetail

data class CoinDetailState(
    val screenData: ScreenData = ScreenData.Initial
)

sealed class ScreenData {
    data object Initial: ScreenData()
    data object Loading: ScreenData()
    data class Error(
        val message: String
    ): ScreenData()
    data class Data(
        val coin: CoinDetail? = null
    ) : ScreenData()
}