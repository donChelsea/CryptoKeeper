package com.example.cryptokeeper.presentation.screens.search

import android.content.Context
import androidx.lifecycle.viewModelScope
import com.example.cryptokeeper.common.Resource
import com.example.cryptokeeper.common.SharedPreferencesModule
import com.example.cryptokeeper.data.repository.ConnectivityModule
import com.example.cryptokeeper.domain.model.Coin
import com.example.cryptokeeper.domain.use_cases.GetCoinsUseCase
import com.example.cryptokeeper.presentation.CryptoKeeperViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    @ApplicationContext val context: Context,
    private val getCoinsUseCase: GetCoinsUseCase,
    private val sharedPref: SharedPreferencesModule,
    private val connectivityModule: ConnectivityModule,
) : CryptoKeeperViewModel<SearchState, SearchEvent, SearchAction>() {

    private val _state = MutableStateFlow(SearchState())
    override val state: StateFlow<SearchState>
        get() = _state.asStateFlow()

    private val allCoins = mutableListOf<Coin>()
    val searchHistory = sharedPref.searchHistory

    init {
        viewModelScope.launch {
            connectivityModule.isConnected.collectLatest { isConnected ->
                if (isConnected) {
                    updateState(ScreenData.Loading)
                    getCoins()
                } else {
                    updateState(ScreenData.Offline)
                }
            }
        }
    }

    override fun handleAction(action: SearchAction) {
        when (action) {
            is SearchAction.OnSearchClicked -> {
                searchCoins(action.query)
                sharedPref.put(action.query)
            }
            is SearchAction.OnSearchHistoryItemClicked -> searchCoins(action.query)
            is SearchAction.OnCoinClicked -> emitUiEvent(SearchEvent.OnCoinClicked(action.coinId, action.coinName))
            is SearchAction.OnClearSearchHistoryItem -> sharedPref.clear(action.query)
        }
    }

    private fun getCoins() {
        viewModelScope.launch {
            getCoinsUseCase().collectLatest { result ->
                when (result) {
                    is Resource.Success -> {
                        allCoins.addAll(result.data.orEmpty())
                        updateState(ScreenData.Data(data = allCoins))
                    }
                    is Resource.Error -> updateState(ScreenData.Error(message = result.message ?: "An unexpected error occurred."))
                    is Resource.Loading -> updateState(ScreenData.Loading)
                }
            }
        }
    }

    private fun searchCoins(query: String) {
        updateState(ScreenData.Loading)

        val results = mutableListOf<Coin>()
        allCoins.forEach { coin ->
            val lowercaseQuery = query.lowercase()
            val lowercaseName = coin.name.lowercase()
            val lowercaseSym = coin.symbol.lowercase()

            if (lowercaseName.contains(lowercaseQuery) || lowercaseSym.contains(lowercaseQuery)) {
                results.add(coin)
            }
        }

        updateState(ScreenData.Data(results = results))
    }

    private fun updateState(screenData: ScreenData) =
        _state.update { it.copy(screenData = screenData) }
}
