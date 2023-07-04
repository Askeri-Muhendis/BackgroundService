package com.ibrahimethemsen.backgroundservice

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.CountDownTimer
import android.os.IBinder
import android.provider.Settings
import android.util.Log

class NewService : Service() {
    private lateinit var player:MediaPlayer
    private val countDownVariable = object : CountDownTimer(600_000,1_000){
        override fun onTick(millisUntilFinished: Long) {
            val second = millisUntilFinished/1000
            Log.d(TAG, "onTick: $second")
        }

        override fun onFinish() {
            Log.d(TAG, "onFinish: Finish")
        }

    }
    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        player = MediaPlayer.create(this, Settings.System.DEFAULT_RINGTONE_URI)
        player.setLooping(true)
        Log.d(TAG, "onStartCommand: Start Service")
        player.start()
        countDownVariable.start()
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy: Service")
        countDownVariable.cancel()
        player.stop()
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }
    companion object{
        const val TAG = "CountDownTimer"
    }
}

