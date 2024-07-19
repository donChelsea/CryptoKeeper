package com.example.cryptokeeper.di

import android.content.Context
import com.example.cryptokeeper.common.Constants.BASE_URL
import com.example.cryptokeeper.common.SharedPreferencesModel
import com.example.cryptokeeper.data.remote.CoinPaprikaApi
import com.example.cryptokeeper.data.repository.CoinRepositoryImpl
import com.example.cryptokeeper.data.repository.ConnectivityRepository
import com.example.cryptokeeper.domain.repository.CoinRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideCoinPaprikaApi(): CoinPaprikaApi =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CoinPaprikaApi::class.java)

    @Provides
    @Singleton
    fun provideCoinRepository(api: CoinPaprikaApi): CoinRepository = CoinRepositoryImpl(api)

    @Provides
    @Singleton
    fun provideSharedPreferencesModel(@ApplicationContext context: Context): SharedPreferencesModel =
        SharedPreferencesModel(context)

    @Provides
    @Singleton
    fun provideConnectivityRespository(@ApplicationContext context: Context): ConnectivityRepository =
        ConnectivityRepository(context)
}