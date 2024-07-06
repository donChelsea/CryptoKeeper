package com.example.cryptokeeper.domain.repository

import com.example.cryptokeeper.data.remote.dtos.CoinDetailDto
import com.example.cryptokeeper.data.remote.dtos.CoinDto

interface CoinRepository {
    suspend fun getCoins(): List<CoinDto>
    suspend fun getCoinById(coinId: String): CoinDetailDto
}