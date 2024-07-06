package com.example.cryptokeeper.presentation.screens.list.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.cryptokeeper.domain.model.Coin
import com.example.cryptokeeper.presentation.composables.CoinListItem
import com.example.cryptokeeper.presentation.composables.ShowError
import com.example.cryptokeeper.presentation.composables.ShowLoading
import com.example.cryptokeeper.presentation.navigation.NavScreen
import com.example.cryptokeeper.presentation.screens.list.CoinListAction
import com.example.cryptokeeper.presentation.screens.list.CoinListEvent
import com.example.cryptokeeper.presentation.screens.list.CoinListState
import com.example.cryptokeeper.presentation.screens.list.CoinListViewModel
import com.example.cryptokeeper.presentation.screens.list.ScreenData

@Composable
fun CoinListScreen(
    viewModel: CoinListViewModel = hiltViewModel(),
    navController: NavHostController,
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(key1 = true) {
        viewModel.events.collect { event ->
            when (event) {
                is CoinListEvent.OnCoinClicked -> navController.navigate(NavScreen.CoinDetail.withArgs(event.coinId))
            }
        }
    }

    CoinListLayout(
        state = state,
        onAction = viewModel::handleAction
    )
}

@Composable
private fun CoinListLayout(
    state: CoinListState,
    onAction: (CoinListAction) -> Unit
) {
    when(state.screenData) {
        is ScreenData.Initial -> {}
        is ScreenData.Offline -> {}
        is ScreenData.Loading -> ShowLoading()
        is ScreenData.Error -> ShowError()
        is ScreenData.Data -> CoinListContent(
            coins = state.screenData.coins,
            onItemClick = { onAction(CoinListAction.OnCoinClicked(it)) },
        )
    }
}

@Composable
private fun CoinListContent(
    coins: List<Coin>,
    onItemClick: (String) -> Unit,
) {
    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(coins) { coin ->
                CoinListItem(
                    coin = coin,
                    onItemClick = { onItemClick(coin.id) }
                )
            }
        }
    }
}