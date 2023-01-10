package com.example.deafen_prototype;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;
import android.content.Intent;

public class MainActivity extends AppCompatActivity {

    private AudioManager audioManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        audioManager = (AudioManager) getApplicationContext().getSystemService(Context.AUDIO_SERVICE);
    }

    public void Deafen(View view){
        int volume_level= audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);  //captures volume prior to change
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, 1, AudioManager.FLAG_SHOW_UI);  //lowers volume to 1
        Toast.makeText(this,"Deafening", Toast.LENGTH_SHORT).show();
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Do something after 3s = 3000ms
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, volume_level, AudioManager.FLAG_SHOW_UI);
            }
        }, 3000);
    }

    public void Mute(View view){
        int volume_level= audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        audioManager.adjustVolume(AudioManager.ADJUST_MUTE, AudioManager.FLAG_SHOW_UI); //mutes volume
        Toast.makeText(this,"Muting", Toast.LENGTH_SHORT).show();
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Do something after 3s = 3000ms
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, volume_level, AudioManager.FLAG_SHOW_UI);
            }
        }, 3000);
    }
}