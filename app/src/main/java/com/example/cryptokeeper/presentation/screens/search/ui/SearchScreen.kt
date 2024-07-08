package com.example.cryptokeeper.presentation.screens.search.ui

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.cryptokeeper.presentation.screens.search.SearchViewModel

@Composable
fun SearchScreen(
    viewModel: SearchViewModel = hiltViewModel(),
    navController: NavHostController,
) {
//    val state by viewModel.state.collectAsState()

//    when(state.screenData) {
//        is ScreenData.Initial -> {}
//        is ScreenData.Offline -> {}
//        is ScreenData.Loading -> ShowLoading()
//        is ScreenData.Error -> ShowError()
//        is ScreenData.Data -> CoinList(
//            coins = (state.screenData as ScreenData.Data).coins,
//            onItemClick = {}
//        )
//    }
}