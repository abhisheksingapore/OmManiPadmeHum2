package com.tatsme.ommanipadmehum;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

/**
 * Created by abhishek on 25/9/16.
 */

public class BackgroundAudioService extends Service implements MediaPlayer.OnCompletionListener {
    MediaPlayer mediaPlayer;

    @Override
    public IBinder onBind (Intent intent) {
        return null;
    }

    @Override
    public void onCreate (){
        mediaPlayer = MediaPlayer.create(this,R.raw.original_extended_version2);
        mediaPlayer.setOnCompletionListener(this);
    }

    public int onStartCommand (Intent intent, int flags, int startID){
        mediaPlayer.start();
        return START_STICKY;
    }

    public void onDestroy () {
        if (mediaPlayer.isPlaying()){
            mediaPlayer.stop();
        }
        mediaPlayer.release();
    }

    public void onCompletion (MediaPlayer _mediaplayer){
        stopSelf();
    }
}
