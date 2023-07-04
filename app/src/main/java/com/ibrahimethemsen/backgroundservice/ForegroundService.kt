package com.ibrahimethemsen.backgroundservice

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.Build
import android.os.CountDownTimer
import android.os.IBinder
import android.provider.Settings

import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat

/***
 * https://androidwave.com/foreground-service-android-example/
 */

class ForegroundService : Service() {
    private lateinit var player: MediaPlayer
    private val countDownVariable = object : CountDownTimer(600_000, 1_000) {
        override fun onTick(millisUntilFinished: Long) {
            val second = millisUntilFinished / 1000
            Log.d(TAG, "FonTick: $second")
        }

        override fun onFinish() {
            Log.d(TAG, "FonFinish: Finish")
        }
    }

    override fun onCreate() {
        super.onCreate()
        player = MediaPlayer.create(this, Settings.System.DEFAULT_RINGTONE_URI)
        player.isLooping = true
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        startForegroundService()
        player.start()
        countDownVariable.start()
        return START_STICKY
    }

    private fun startForegroundService() {
        val channelId =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                createNotificationChannel("my_service", "My Service")
            } else {
                ""
            }

        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setContentTitle("Foreground Service")
            .setContentText("Service is running")
            .setSmallIcon(R.drawable.ic_launcher_background)

        val notification = notificationBuilder.build()

        startForeground(1, notification)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(channelId: String, channelName: String): String {
        val channel = NotificationChannel(
            channelId,
            channelName,
            NotificationManager.IMPORTANCE_DEFAULT
        )
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
        return channelId
    }

    override fun onDestroy() {
        super.onDestroy()
        countDownVariable.cancel()
        player.stop()
        player.release()
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    companion object {
        const val TAG = "Foreground CountDownTimer"
    }
}