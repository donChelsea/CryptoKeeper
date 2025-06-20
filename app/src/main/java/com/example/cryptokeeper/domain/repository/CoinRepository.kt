package com.example.cryptokeeper.domain.repository

import com.example.cryptokeeper.domain.util.Resource
import com.example.cryptokeeper.domain.model.Coin
import com.example.cryptokeeper.domain.model.CoinDetail
import kotlinx.coroutines.flow.Flow

interface CoinRepository {
    fun getCoinsFlow(): Flow<Resource<List<Coin>>>
    suspend fun getCoinById(coinId: String): Resource<CoinDetail>
}