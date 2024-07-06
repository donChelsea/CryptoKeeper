package com.example.cryptokeeper.common

import android.content.Context
import android.net.ConnectivityManager

object Utils {
    val Context.isConnected: Boolean
        get() {
            return (getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager)
                .activeNetworkInfo?.isConnected == true
        }
}