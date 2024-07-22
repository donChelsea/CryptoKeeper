package com.example.cryptokeeper.di

import com.example.cryptokeeper.data.repository.CoinRepositoryImpl
import com.example.cryptokeeper.domain.repository.CoinRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindCoinRepository(coinRepositoryImpl: CoinRepositoryImpl): CoinRepository
}