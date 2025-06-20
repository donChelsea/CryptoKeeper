package com.example.cryptokeeper.data.repository

import com.example.cryptokeeper.data.remote.CoinApi
import com.example.cryptokeeper.domain.util.Resource
import com.example.cryptokeeper.domain.model.Coin
import com.example.cryptokeeper.domain.model.CoinDetail
import com.example.cryptokeeper.domain.repository.CoinRepository
import com.example.cryptokeeper.domain.util.safeCall
import com.example.cryptokeeper.domain.util.safeFlow
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CoinRepositoryImpl @Inject constructor(
    private val api: CoinApi
) : CoinRepository {
    override fun getCoinsFlow(): Flow<Resource<List<Coin>>> = safeFlow {
        api.getCoins().map { it.toDomain() }
    }

    override suspend fun getCoinById(coinId: String): Resource<CoinDetail> = safeCall {
        api.getCoinById(coinId).toDomain()
    }
}