package com.example.cryptokeeper.presentation.screens.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.cryptokeeper.common.Resource
import com.example.cryptokeeper.domain.use_cases.GetCoinByIdUseCase
import com.example.cryptokeeper.presentation.CryptoKeeperViewModel
import com.example.cryptokeeper.presentation.navigation.NavScreen.MovieDetailArgs.COIN_ID
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CoinDetailViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getCoinByIdUseCase: GetCoinByIdUseCase,
) : CryptoKeeperViewModel<CoinDetailState, CoinDetailEvent, CoinDetailAction>() {

    private val _state = MutableStateFlow(CoinDetailState())
    override val state: StateFlow<CoinDetailState>
        get() = _state.asStateFlow()

    private val coinId = savedStateHandle[COIN_ID] ?: ""

    init {
        if (coinId.isNotEmpty()) {
            updateState(coinId = coinId, screenData = ScreenData.Loading)
            getCoinById(coinId)
        } else {
            throw IllegalArgumentException("CoinId cannot be empty.")
        }
    }

    override fun handleAction(action: CoinDetailAction) = when(action) {
        CoinDetailAction.OnBackClicked -> emitUiEvent(CoinDetailEvent.OnBackClicked)
    }

    private fun getCoinById(coinId: String) {
        viewModelScope.launch {
            getCoinByIdUseCase(coinId = coinId).collectLatest { result ->
                when (result) {
                    is Resource.Success -> result.data?.let { coin -> updateState(ScreenData.Data(coin = coin)) }
                    is Resource.Error -> updateState(ScreenData.Error(message = result.message ?: "An unexpected error occurred."))
                    is Resource.Loading -> updateState(ScreenData.Loading)
                }
            }
        }
    }

    private fun updateState(
        screenData: ScreenData = _state.value.screenData,
        coinId: String = _state.value.coinId,
    ) =
        _state.update {
            it.copy(
                screenData = screenData,
                coinId = coinId
            )
        }
}