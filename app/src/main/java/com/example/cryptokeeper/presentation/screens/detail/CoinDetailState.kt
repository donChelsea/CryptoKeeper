package com.example.cryptokeeper.presentation.screens.detail

import androidx.compose.runtime.Immutable
import com.example.cryptokeeper.domain.model.CoinDetail
import com.example.cryptokeeper.presentation.screens.home.HomeEvent

@Immutable
data class CoinDetailState(
    val screenData: ScreenData = ScreenData.Initial,
    val coinId: String = "",
)

@Immutable
sealed class ScreenData {
    data object Initial : ScreenData()
    data object Loading : ScreenData()
    data object Offline : ScreenData()

    @Immutable
    data class Error(
        val message: String,
    ) : ScreenData()

    @Immutable
    data class Data(
        val coin: CoinDetail? = null,
    ) : ScreenData()
}

@Immutable
sealed class CoinDetailEvent {
    data object OnBackClicked : CoinDetailEvent()
}

@Immutable
sealed class CoinDetailAction {
    data object OnBackClicked : CoinDetailAction()
}