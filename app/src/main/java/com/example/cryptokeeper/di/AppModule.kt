package com.example.cryptokeeper.di

import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.cryptokeeper.R
import com.example.cryptokeeper.common.Constants.BASE_URL
import com.example.cryptokeeper.common.NotificationsModule
import com.example.cryptokeeper.common.SharedPreferencesModule
import com.example.cryptokeeper.data.remote.CoinPaprikaApi
import com.example.cryptokeeper.data.repository.ConnectivityModule
import com.example.cryptokeeper.presentation.MainActivity
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
    fun provideSharedPreferencesModule(@ApplicationContext context: Context): SharedPreferencesModule =
        SharedPreferencesModule(context)

    @Provides
    @Singleton
    fun provideConnectivityModule(@ApplicationContext context: Context): ConnectivityModule =
        ConnectivityModule(context)

    @Provides
    @Singleton
    fun provideNotificationsModule(@ApplicationContext context: Context): NotificationsModule =
        NotificationsModule(context)
}