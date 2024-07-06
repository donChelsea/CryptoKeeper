package com.example.cryptokeeper.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.cryptokeeper.presentation.navigation.NavScreen.MovieDetailArgs.COIN_ID
import com.example.cryptokeeper.presentation.screens.detail.ui.CoinDetailScreen
import com.example.cryptokeeper.presentation.screens.list.ui.CoinListScreen
import com.example.cryptokeeper.presentation.screens.search.ui.SearchScreen

@Composable
fun NavigationHost() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = NavScreen.CoinList.route
    ) {
        composable(route = NavScreen.CoinList.route) {
            CoinListScreen(navController = navController)
        }
        composable(
            route = NavScreen.CoinDetail.route + "/{$COIN_ID}",
            arguments = listOf(
                navArgument(COIN_ID) {
                    type = NavType.StringType
                }
            )
        ) {
            CoinDetailScreen(navController = navController)
        }
        composable(route = NavScreen.Search.route) {
            SearchScreen(navController = navController)
        }
    }
}