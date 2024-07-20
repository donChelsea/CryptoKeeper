package com.example.cryptokeeper.presentation.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.cryptokeeper.R
import com.example.cryptokeeper.presentation.composables.NavTopBar
import com.example.cryptokeeper.presentation.navigation.NavScreen.MovieDetailArgs.COIN_ID
import com.example.cryptokeeper.presentation.navigation.NavScreen.MovieDetailArgs.COIN_NAME
import com.example.cryptokeeper.presentation.screens.detail.ui.CoinDetailScreen
import com.example.cryptokeeper.presentation.screens.home.ui.HomeScreen
import com.example.cryptokeeper.presentation.screens.search.ui.SearchScreen

@Composable
fun NavigationHost() {
    var title by remember { mutableStateOf("") }
    val canNavigateBack = (title != NavScreen.Home.name) && title.isNotEmpty()
    val isSearchScreen = title == NavScreen.Search.name
    val navController = rememberNavController()

    Scaffold(
        topBar = {
            NavTopBar(
                title = title,
                canNavigateBack = canNavigateBack,
                navigateUp = { navController.popBackStack() },
                actions = {
                    if (!isSearchScreen) {
                        IconButton(onClick = { navController.navigate(NavScreen.Search.route) }) {
                            Icon(Icons.Default.Search, contentDescription = stringResource(id = R.string.search))
                        }
                    }
                }
            )
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = NavScreen.Home.route,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(route = NavScreen.Home.route) {
                HomeScreen(navController = navController)
                title = NavScreen.Home.name
            }
            composable(
                route = NavScreen.CoinDetail.route + "/{$COIN_ID}/{$COIN_NAME}",
                arguments = listOf(
                    navArgument(COIN_ID) {
                        type = NavType.StringType
                    },
                    navArgument(COIN_NAME) {
                        type = NavType.StringType
                    },
                )
            ) {
                CoinDetailScreen(navController = navController)
                title = it.arguments?.getString(COIN_NAME) ?: NavScreen.CoinDetail.name
            }
            composable(route = NavScreen.Search.route) {
                SearchScreen(navController = navController)
                title = NavScreen.Search.name
            }
        }
    }
}