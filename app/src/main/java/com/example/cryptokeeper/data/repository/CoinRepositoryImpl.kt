package com.example.cryptokeeper.data.repository

import com.example.cryptokeeper.data.remote.CoinPaprikaApi
import com.example.cryptokeeper.data.remote.dtos.CoinDetailDto
import com.example.cryptokeeper.data.remote.dtos.CoinDto
import com.example.cryptokeeper.domain.model.Coin
import com.example.cryptokeeper.domain.model.CoinDetail
import com.example.cryptokeeper.domain.repository.CoinRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CoinRepositoryImpl @Inject constructor(
    private val api: CoinPaprikaApi
) : CoinRepository {
    override suspend fun getCoins(): List<CoinDto> = api.getCoins()

    override suspend fun getCoinById(coinId: String): CoinDetailDto = api.getCoinById(coinId)
}