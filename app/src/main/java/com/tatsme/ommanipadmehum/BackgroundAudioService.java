package com.tatsme.ommanipadmehum;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;

/**
 * Created by abhishek on 25/9/16.
 */

public class BackgroundAudioService extends Service implements MediaPlayer.OnCompletionListener {
    private final IBinder mBinder = new ServiceBinder();
    MediaPlayer mediaPlayer;
    boolean loopMusicFlag = true;

    public void onCreate() {
        super.onCreate();
        startMediaPlayer();
    }

    @Override
    public IBinder onBind (Intent intent) {
        return mBinder;
    }

    public void startMediaPlayer() {
        mediaPlayer = MediaPlayer.create(this,R.raw.original_extended_version2);
        mediaPlayer.setOnCompletionListener(this);
        mediaPlayer.start();
    }

    public void onDestroy () {
        if (mediaPlayer.isPlaying()){
            mediaPlayer.stop();
        }
        mediaPlayer.release();
    }

    public void pauseMusic() {
        mediaPlayer.pause();
    }

    public void stopMusic() {
        mediaPlayer.stop();
    }

    public void playMusic() {
        mediaPlayer.start();
    }

    public long getTotalPlaytime() {
        return mediaPlayer.getDuration();
    }

    public long getCurrentPlaytime() {
        return mediaPlayer.getCurrentPosition();
    }

    public void setLoopMusicFlag() {
        loopMusicFlag = !loopMusicFlag;
    }

    public void onCompletion(MediaPlayer mp) {
        if (loopMusicFlag) startMediaPlayer();
        else {
            stopSelf();
            System.exit(0);
        }
    }

    public class ServiceBinder extends Binder {
        BackgroundAudioService getService() {
            return BackgroundAudioService.this;
        }
    }
}
