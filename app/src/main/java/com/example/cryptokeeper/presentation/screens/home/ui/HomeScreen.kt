package com.example.cryptokeeper.presentation.screens.home.ui

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
import com.example.cryptokeeper.presentation.composables.ShowLoadingListWithShimmer
import com.example.cryptokeeper.presentation.composables.ShowOffline
import com.example.cryptokeeper.presentation.navigation.NavScreen
import com.example.cryptokeeper.presentation.screens.home.HomeAction
import com.example.cryptokeeper.presentation.screens.home.HomeEvent
import com.example.cryptokeeper.presentation.screens.home.HomeState
import com.example.cryptokeeper.presentation.screens.home.HomeViewModel
import com.example.cryptokeeper.presentation.screens.home.ScreenData

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    navController: NavHostController,
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(key1 = true) {
        viewModel.events.collect { event ->
            when (event) {
                is HomeEvent.OnCoinClicked -> navController.navigate(
                    NavScreen.CoinDetail.withArgs(event.coinId, event.coinName)
                )
            }
        }
    }

    HomeLayout(
        state = state,
        onAction = viewModel::handleAction
    )
}

@Composable
private fun HomeLayout(
    state: HomeState,
    onAction: (HomeAction) -> Unit,
) {
    when (state.screenData) {
        is ScreenData.Initial -> {}
        is ScreenData.Offline -> ShowOffline()
        is ScreenData.Loading -> ShowLoadingListWithShimmer()
        is ScreenData.Error -> ShowError()
        is ScreenData.Data -> HomeContent(
            coins = state.screenData.coins,
            onItemClick = { id, name ->
                onAction(
                    HomeAction.OnCoinClicked(
                        coinId = id,
                        coinName = name
                    )
                )
            },
        )
    }
}

@Composable
private fun HomeContent(
    coins: List<Coin>,
    onItemClick: (String, String) -> Unit,
) {
    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(coins) { coin ->
                CoinListItem(
                    coin = coin,
                    onItemClick = { id, name -> onItemClick(id, name) }
                )
            }
        }
    }
}