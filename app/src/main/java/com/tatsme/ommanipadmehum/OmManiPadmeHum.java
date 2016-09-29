package com.tatsme.ommanipadmehum;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ToggleButton;


public class OmManiPadmeHum extends AppCompatActivity{
    Intent playbackServiceIntent;
    BackgroundAudioService backgroundAudioService;
    boolean mIsBound = false;
    String mediaPlayerState = null;
    ToggleButton toggleButton = null;
    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            BackgroundAudioService.ServiceBinder serviceBinder = (BackgroundAudioService.ServiceBinder) service;
            backgroundAudioService = serviceBinder.getService();
            mIsBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mIsBound = false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_om_mani_padme_hum);
    }

    @Override
    protected void onStart() {
        super.onStart();
        playbackServiceIntent = new Intent(this, BackgroundAudioService.class);
        bindService(playbackServiceIntent, serviceConnection, Context.BIND_AUTO_CREATE);
        mediaPlayerState = "Playing";

        // Set Music Loop to 'On'
        toggleButton = (ToggleButton) findViewById(R.id.toggleButton);
        toggleButton.setChecked(true);


    }

    public void playPauseMusic(View view) {
        if (mIsBound) {
            switch (mediaPlayerState) {
                case "Playing":
                    pauseMusic(view);
                    break;
                case "Paused":
                    playMusic(view);
                    break;
                case "Stopped":
                    restartMusic(view);
                    break;
            }
        }

    }

    public void pauseMusic(View view) {
        Button playPauseButton = (Button) findViewById(R.id.PlayPauseButton);
        playPauseButton.setText("Play");
        backgroundAudioService.pauseMusic();
        mediaPlayerState = "Paused";
    }

    public void playMusic(View view) {
        Button playPauseButton = (Button) findViewById(R.id.PlayPauseButton);
        playPauseButton.setText("Pause");
        backgroundAudioService.playMusic();
        mediaPlayerState = "Playing";
    }

    public void restartMusic(View view) {
        if (mIsBound) {
            backgroundAudioService.startMediaPlayer();
            Button playPauseButton = (Button) findViewById(R.id.PlayPauseButton);
            playPauseButton.setText("Pause");
            mediaPlayerState = "Playing";
        }
    }

    public void stopMusic(View view) {
        if (mIsBound) {
            backgroundAudioService.stopMusic();
            Button playPauseButton = (Button) findViewById(R.id.PlayPauseButton);
            playPauseButton.setText("Play");
            mediaPlayerState = "Stopped";
        }
    }

    public void loopMusic(View view) {
        backgroundAudioService.setLoopMusicFlag();
    }

}


