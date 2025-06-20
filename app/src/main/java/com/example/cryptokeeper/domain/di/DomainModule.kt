package com.example.cryptokeeper.domain.di

import android.content.Context
import com.example.cryptokeeper.domain.modules.ConnectivityModule
import com.example.cryptokeeper.domain.modules.SharedPreferencesModule
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DomainModule {

    @Provides
    @Singleton
    fun provideSharedPreferencesModule(@ApplicationContext context: Context): SharedPreferencesModule =
        SharedPreferencesModule(context)

    @Provides
    @Singleton
    fun provideConnectivityModule(@ApplicationContext context: Context): ConnectivityModule =
        ConnectivityModule(context)
}