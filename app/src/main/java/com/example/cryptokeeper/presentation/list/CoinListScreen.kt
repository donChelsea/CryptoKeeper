package com.example.cryptokeeper.presentation.list

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.cryptokeeper.domain.model.Coin
import com.example.cryptokeeper.presentation.composables.CoinListItem
import com.example.cryptokeeper.presentation.composables.ShowError
import com.example.cryptokeeper.presentation.composables.ShowLoading

@Composable
fun CoinListScreen(
    viewModel: CoinListViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    when(state.screenData) {
        is ScreenData.Initial -> {}
        is ScreenData.Offline -> {}
        is ScreenData.Loading -> ShowLoading()
        is ScreenData.Error -> ShowError()
        is ScreenData.Data -> CoinList(
            coins = (state.screenData as ScreenData.Data).coins,
            onItemClick = {}
        )
    }
}

@Composable
private fun CoinList(
    coins: List<Coin>,
    onItemClick: (Coin) -> Unit,
) {
    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(coins) { coin ->
                CoinListItem(
                    coin = coin,
                    onItemClick = onItemClick
                )
            }
        }
    }
}