package com.example.cryptokeeper.presentation.navigation

sealed class NavScreen(val route: String) {
    data object CoinList: NavScreen("coinList")
    data object CoinDetail: NavScreen("coinDetail")
    data object Search: NavScreen("Search")

    object MovieDetailArgs {
        const val COIN_ID = "coinId"
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