package com.tatsme.ommanipadmehum;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class OmManiPadmeHum extends AppCompatActivity{
    Button startPlayButton, stopPlayButton;
    Intent playbackServiceIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_om_mani_padme_hum);

        playbackServiceIntent = new Intent(this, BackgroundAudioService.class);
        startService(playbackServiceIntent);
    }

    public void startMusic (View v){
        startService(playbackServiceIntent);
    }

    public void stopMusic (View v) {
        stopService(playbackServiceIntent);
    }

}


