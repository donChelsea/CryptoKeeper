package com.example.cryptokeeper.data.di

import com.example.cryptokeeper.BuildConfig
import com.example.cryptokeeper.data.remote.CoinApi
import com.example.cryptokeeper.data.repository.CoinRepositoryImpl
import com.example.cryptokeeper.domain.repository.CoinRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    @Singleton
    fun provideCoinApi(): CoinApi =
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CoinApi::class.java)

    @Provides
    @Singleton
    fun provideCoinRepository(api: CoinApi): CoinRepository =
        CoinRepositoryImpl(api)
}