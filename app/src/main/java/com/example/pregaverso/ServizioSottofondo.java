package com.example.pregaverso;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

import androidx.annotation.Nullable;

public class ServizioSottofondo extends Service {

    MediaPlayer mediap;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        mediap = MediaPlayer.create(this, R.raw.sottofondostoria);
        if(!mediap.isPlaying()){
            mediap.setLooping(true);
            mediap.start();
        }
        return super.onStartCommand(intent,flags,startId);
    }

    @Override
    public void onDestroy(){
        mediap.stop();
        super.onDestroy();
    }


}
