package com.example.cryptokeeper.presentation.screens.search

import androidx.compose.runtime.Immutable
import com.example.cryptokeeper.domain.model.Coin
import com.example.cryptokeeper.presentation.models.CoinUiModel

@Immutable
data class SearchState(
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
        val data: List<CoinUiModel> = emptyList(),
        val results: List<CoinUiModel> = emptyList(),
    ) : ScreenData()
}


@Immutable
sealed class SearchEvent {
    data object OnSearchClicked : SearchEvent()
    data class OnCoinClicked(val coinId: String, val coinName: String): SearchEvent()
}

@Immutable
sealed class SearchAction {
    data class OnSearchClicked(val query: String) : SearchAction()
    data class OnSearchHistoryItemClicked(val query: String) : SearchAction()
    data class OnCoinClicked(val coinId: String, val coinName: String): SearchAction()
    data class OnClearSearchHistoryItem(val query: String): SearchAction()
}