package com.example.pregaverso

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder

class ServizioSottofondo : Service() {
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val mediap = MediaPlayer.create(this, R.raw.sottofondostoria)

        mediap.setLooping(true)


        return super.onStartCommand(intent, flags, startId)
    }


}