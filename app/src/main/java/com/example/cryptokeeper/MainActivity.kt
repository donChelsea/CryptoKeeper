package com.example.cryptokeeper

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.example.cryptokeeper.presentation.navigation.NavigationHost
import com.example.cryptokeeper.presentation.ui.theme.CryptoKeeperTheme
import com.example.cryptokeeper.presentation.receivers.ToastReceiver
import com.example.cryptokeeper.presentation.receivers.ToastReceiver.Companion.MESSAGE_ARG
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val eventsChannel = NotificationChannel(
                "coin_events",
                "Coin Events",
                NotificationManager.IMPORTANCE_HIGH
            )
            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(eventsChannel)
        }

        setContent {
            CryptoKeeperTheme {
                var hasNotificationPermission by remember {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        mutableStateOf(
                            ContextCompat.checkSelfPermission(
                                this,
                                Manifest.permission.POST_NOTIFICATIONS
                            ) == PackageManager.PERMISSION_GRANTED
                        )
                    } else {
                        mutableStateOf(true)
                    }
                }
                val permissionLauncher = rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.RequestPermission(),
                    onResult = { isGranted -> hasNotificationPermission = isGranted }
                )
                if (hasNotificationPermission) showNotification()
                else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    SideEffect {
                        permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                    }
                }

                window.statusBarColor = MaterialTheme.colorScheme.background.toArgb()
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    NavigationHost()
                }
            }
        }
    }

    private fun showNotification() {
        val intent = Intent(this, ToastReceiver::class.java).apply {
            putExtra(MESSAGE_ARG, "Clicked!")
        }
        val flag =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                PendingIntent.FLAG_IMMUTABLE
            else 0
        val pendingIntent = PendingIntent.getBroadcast(
            this,
            0,
            intent,
            flag
        )
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notification = NotificationCompat.Builder(this, "coin_events")
            .setContentTitle(this.getString(R.string.get_live_updates_title))
            .setContentText(this.getString(R.string.get_live_updates_description))
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .addAction(0, this.getString(R.string.show), pendingIntent)
            .build()
        notificationManager.notify(1, notification)
    }
}