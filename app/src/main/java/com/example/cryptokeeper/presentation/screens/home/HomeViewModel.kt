package com.example.cryptokeeper.presentation.screens.home

import androidx.lifecycle.viewModelScope
import com.example.cryptokeeper.common.Resource
import com.example.cryptokeeper.data.repository.ConnectivityRepository
import com.example.cryptokeeper.domain.use_cases.GetCoinsUseCase
import com.example.cryptokeeper.presentation.CryptoKeeperViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getCoinsUseCase: GetCoinsUseCase,
    private val connectivityRepository: ConnectivityRepository
) : CryptoKeeperViewModel<HomeState, HomeEvent, HomeAction>() {

    private val _state = MutableStateFlow(HomeState())
    override val state: StateFlow<HomeState>
        get() = _state.asStateFlow()

    init {
        viewModelScope.launch {
            connectivityRepository.isConnected.collectLatest { isConnected ->
                if (isConnected) {
                    updateState(ScreenData.Loading)
                    getCoins()
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

    private fun getCoins() {
        viewModelScope.launch {
            getCoinsUseCase().collectLatest { result ->
                when (result) {
                    is Resource.Success -> updateState(ScreenData.Data(coins = result.data.orEmpty()))
                    is Resource.Error -> updateState(ScreenData.Error(message = result.message ?: "An unexpected error occurred."))
                    is Resource.Loading -> updateState(ScreenData.Loading)
                }
            }
        }
    }

    private fun updateState(screenData: ScreenData) =
        _state.update { it.copy(screenData = screenData) }
}