package com.example.cryptokeeper.presentation.navigation

sealed class NavScreen(
    val route: String,
    val name: String,
) {
    data object Home : NavScreen("home", "Home")
    data object CoinDetail : NavScreen("coinDetail", "Coin Detail")
    data object Search : NavScreen("search", "Search")

    object MovieDetailArgs {
        const val COIN_ID = "coinId"
        const val COIN_NAME = "coinName"
    }

    fun withArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }
}