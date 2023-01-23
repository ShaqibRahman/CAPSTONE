package com.example.deafen_prototype;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private AudioManager audioManager;
    private ImageButton settingsbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                int volume_level= audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);  //captures volume prior to change
                if (volume_level == 15){
                    audioManager.adjustVolume(AudioManager.ADJUST_MUTE, AudioManager.FLAG_SHOW_UI); //mutes volume
                }
                handler.postDelayed(this,2000);
            }
        },20000);

        settingsbutton = (ImageButton) findViewById(R.id.Settings);
        settingsbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenSettings();
            }
        });

        audioManager = (AudioManager) getApplicationContext().getSystemService(Context.AUDIO_SERVICE);
    }

    public void Deafen(View view){
        int volume_level= audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);  //captures volume prior to change
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, 5, AudioManager.FLAG_SHOW_UI);  //lowers volume to 1
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


    public void Trigger(View view) {

        Bundle extras = getIntent().getExtras();
        Boolean state = null;
        if (extras != null) {
            state = extras.getBoolean("state");
        }
        if (state == true) {
            DeafenTrigger();
        } else {
            PauseTrigger();
        }
    }

    public void DeafenTrigger(){

        Bundle extras = getIntent().getExtras();    //get variables from settings screen
        int volume_level = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);  //captures volume prior to change
        int new_volume = extras.getInt("reduced_vol");
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, new_volume, AudioManager.FLAG_SHOW_UI);  //lowers volume to 1

        int new_time = extras.getInt("deafen_time"); //gets the time preference of the user from the settings

        Toast.makeText(this,"Deafening", Toast.LENGTH_SHORT).show();
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Do something after 3s = 3000ms
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, volume_level, AudioManager.FLAG_SHOW_UI);
            }
        }, new_time*1000);
    }

    public void PauseTrigger(){

        Bundle extras = getIntent().getExtras();    //get variables from settings screen
        Context context = this;
        // stop music player
        AudioManager pause_play = (AudioManager)context.getSystemService(Context.AUDIO_SERVICE);
        pause_play.requestAudioFocus(null,AudioManager.STREAM_MUSIC,AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

        int new_time = extras.getInt("deafen_time"); //gets the time preference of the user from the settings

        Toast.makeText(this,"Pausing", Toast.LENGTH_SHORT).show();
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //resume music player after a few seconds
                pause_play.abandonAudioFocus(null);
            }
        }, new_time*1000);
    }

    public void OpenSettings(){
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

}