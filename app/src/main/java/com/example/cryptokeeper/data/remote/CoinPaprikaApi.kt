package com.example.cryptokeeper.data.remote

import com.example.cryptokeeper.data.remote.dtos.CoinDetailDto
import com.example.cryptokeeper.data.remote.dtos.CoinDto
import retrofit2.http.GET
import retrofit2.http.Path

interface CoinPaprikaApi {

    @GET("/coins")
    suspend fun getCoins(): List<CoinDto>

    @GET("/coins/{coinId}")
    suspend fun getCoinById(
        @Path("coinId") coinId: String
    ): CoinDetailDto
}