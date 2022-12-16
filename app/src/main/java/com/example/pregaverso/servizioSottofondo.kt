package com.example.pregaverso

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder
/*

class ServizioSottofondo : Service() {
    private var mediap : MediaPlayer = MediaPlayer.create(this, R.raw.sottofondostoria)

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        mediap.isLooping = true
        mediap.start()
        return super.onStartCommand(intent, flags, startId)
    }

    override fun stopService(name: Intent?): Boolean {
        mediap.stop()
        return super.stopService(name)
    }

    override fun onDestroy() {
        mediap.stop()
        super.onDestroy()
    }
}
 */


