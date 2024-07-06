package com.example.cryptokeeper.presentation.screens.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cryptokeeper.common.Resource
import com.example.cryptokeeper.domain.use_cases.GetCoinsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SearchViewModel @Inject constructor(
    private val getCoinsUseCase: GetCoinsUseCase,
) : ViewModel() {
//
//    private val _state = MutableStateFlow(CoinListState())
//    val state: StateFlow<CoinListState>
//        get() = _state.asStateFlow()
//
//    init {
//        updateState(ScreenData.Loading)
//        getCoins()
//    }
//
//    private fun getCoins() {
//        viewModelScope.launch {
//            getCoinsUseCase().collectLatest { result ->
//                when (result) {
//                    is Resource.Success -> updateState(ScreenData.Data(coins = result.data.orEmpty()))
//                    is Resource.Error -> updateState(ScreenData.Error(message = result.message ?: "An unexpected error occurred."))
//                    is Resource.Loading -> updateState(ScreenData.Loading)
//                }
//            }
//        }
//    }
//
//    private fun updateState(screenData: ScreenData) =
//        _state.update { it.copy(screenData = screenData) }
}