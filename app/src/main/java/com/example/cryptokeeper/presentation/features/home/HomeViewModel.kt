package com.example.cryptokeeper.presentation.features.home

import androidx.lifecycle.viewModelScope
import com.example.cryptokeeper.domain.modules.ConnectivityModule
import com.example.cryptokeeper.domain.repository.CoinRepository
import com.example.cryptokeeper.domain.util.Resource
import com.example.cryptokeeper.presentation.CryptoKeeperViewModel
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
class HomeViewModel @Inject constructor(
    private val repository: CoinRepository,
    private val connectivityModule: ConnectivityModule
) : CryptoKeeperViewModel<HomeState, HomeEvent, HomeAction>() {

    private val _state = MutableStateFlow(HomeState())
    override val state: StateFlow<HomeState>
        get() = _state.asStateFlow()

    init {
        viewModelScope.launch {
            connectivityModule.isConnected.collectLatest { isConnected ->
                if (isConnected) {
                    updateState(ScreenData.Loading)
                    withContext(Dispatchers.IO) { getCoins() }
                } else {
                    updateState(ScreenData.Offline)
                }
            }
        }
    }

    override fun handleAction(action: HomeAction) = when (action) {
        is HomeAction.OnCoinClicked -> emitUiEvent(
            HomeEvent.OnCoinClicked(
                coinId = action.coinId,
                coinName = action.coinName
            )
        )
    }

    private suspend fun getCoins() {
        repository.getCoinsFlow().collectLatest { result ->
            when (result) {
                is Resource.Success -> result.data?.let { coins ->
                    updateState(ScreenData.Data(coins = coins.map { it.toUiModel() }))
                }

                is Resource.Error -> updateState(
                    ScreenData.Error(result.message ?: "An unexpected error occurred.")
                )

                is Resource.Loading -> updateState(ScreenData.Loading)
            }
        }
    }

    private fun updateState(screenData: ScreenData) =
        _state.update { it.copy(screenData = screenData) }
}