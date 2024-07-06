package com.example.cryptokeeper.presentation.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cryptokeeper.common.Resource
import com.example.cryptokeeper.domain.use_cases.GetCoinByIdUseCase
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
) : ViewModel() {

    private val _state = MutableStateFlow(CoinDetailState())
    val state: StateFlow<CoinDetailState>
        get() = _state.asStateFlow()

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

    private fun updateState(screenData: ScreenData) =
        _state.update { it.copy(screenData = screenData) }
}