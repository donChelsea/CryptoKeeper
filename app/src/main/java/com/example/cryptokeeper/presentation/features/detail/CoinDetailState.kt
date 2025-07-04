package com.example.cryptokeeper.presentation.features.detail

import androidx.compose.runtime.Immutable
import com.example.cryptokeeper.presentation.models.CoinDetailUiModel

@Immutable
data class CoinDetailState(
    val screenData: ScreenData = ScreenData.Initial,
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
        val coin: CoinDetailUiModel = CoinDetailUiModel(),
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