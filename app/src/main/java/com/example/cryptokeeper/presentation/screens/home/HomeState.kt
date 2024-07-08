package com.example.cryptokeeper.presentation.screens.home

import androidx.compose.runtime.Immutable
import com.example.cryptokeeper.domain.model.Coin

@Immutable
data class HomeState(
    val screenData: ScreenData = ScreenData.Initial
)

@Immutable
sealed class ScreenData {
    data object Initial: ScreenData()
    data object Loading: ScreenData()
    data object Offline: ScreenData()

    @Immutable
    data class Error(
        val message: String
    ): ScreenData()

    @Immutable
    data class Data(
        val coins: List<Coin> = emptyList()
    ) : ScreenData()
}

@Immutable
sealed class HomeEvent {
    @Immutable
    data class OnCoinClicked(val coinId: String, val coinName: String): HomeEvent()
}

@Immutable
sealed class HomeAction {
    @Immutable
    data class OnCoinClicked(val coinId: String, val coinName: String): HomeAction()
}