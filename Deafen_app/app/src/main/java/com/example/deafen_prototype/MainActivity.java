package com.example.deafen_prototype;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private AudioManager audioManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        audioManager = (AudioManager) getApplicationContext().getSystemService(Context.AUDIO_SERVICE);
    }

    public void Deafen(View view){
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, 1, AudioManager.FLAG_SHOW_UI);
        Toast.makeText(this,"Deafening", Toast.LENGTH_SHORT).show();
    }

    public void Mute(View view){
        audioManager.adjustVolume(AudioManager.ADJUST_MUTE, AudioManager.FLAG_SHOW_UI);
        Toast.makeText(this,"Muting", Toast.LENGTH_SHORT).show();
    }
}