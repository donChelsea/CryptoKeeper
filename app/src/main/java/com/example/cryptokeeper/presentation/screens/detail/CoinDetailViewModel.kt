package com.example.cryptokeeper.presentation.screens.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.cryptokeeper.domain.modules.ConnectivityModule
import com.example.cryptokeeper.domain.repository.CoinRepository
import com.example.cryptokeeper.domain.util.Resource
import com.example.cryptokeeper.presentation.CryptoKeeperViewModel
import com.example.cryptokeeper.presentation.navigation.NavScreen.MovieDetailArgs.COIN_ID
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CoinDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: CoinRepository,
    private val connectivityModule: ConnectivityModule,
) : CryptoKeeperViewModel<CoinDetailState, CoinDetailEvent, CoinDetailAction>() {

    private val _state = MutableStateFlow(CoinDetailState())
    override val state: StateFlow<CoinDetailState>
        get() = _state.asStateFlow()

    private val coinId: String =
        savedStateHandle[COIN_ID] ?: throw IllegalArgumentException(COIN_ERROR)

    init {
        viewModelScope.launch {
            connectivityModule.isConnected.collectLatest { isConnected ->
                if (isConnected) {
                    updateState(ScreenData.Loading)
                    withContext(Dispatchers.IO) { getCoinById(coinId) }
                } else {
                    updateState(ScreenData.Offline)
                }
            }
        }
    }

    override fun handleAction(action: CoinDetailAction) = when (action) {
        CoinDetailAction.OnBackClicked -> emitUiEvent(CoinDetailEvent.OnBackClicked)
    }

    private fun getCoinById(coinId: String) {
        viewModelScope.launch {
            when (val result = repository.getCoinById(coinId = coinId)) {
                is Resource.Success -> result.data?.let { coin ->
                    updateState(
                        ScreenData.Data(
                            coin = coin.toUiModel()
                        )
                    )
                }

                is Resource.Error -> updateState(
                    ScreenData.Error(
                        message = result.message ?: "An unexpected error occurred."
                    )
                )

                is Resource.Loading -> updateState(ScreenData.Loading)
            }
        }
    }

    private fun updateState(
        screenData: ScreenData
    ) = _state.update { it.copy(screenData = screenData) }

    companion object {
        private const val COIN_ERROR = "CoinId cannot be empty."
    }
}