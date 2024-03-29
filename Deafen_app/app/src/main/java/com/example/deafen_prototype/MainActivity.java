package com.example.deafen_prototype;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.content.Intent;
import android.media.MediaPlayer;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private AudioManager audioManager;
    private ImageButton settingsbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupPermissions();

        settingsbutton = (ImageButton) findViewById(R.id.Settings);
        settingsbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenSettings();
            }
        });

        audioManager = (AudioManager) getApplicationContext().getSystemService(Context.AUDIO_SERVICE);
        Thread deafeningThread = new Thread(new Runnable() {
            public void run() {
               while(true){
                   if(RecordingService.getDeafenFlag()==1) {
                       Log.i("Recording","Sample value recorded above threshold");
                   }
               }


            }
        }, "Deafening Thread");
        deafeningThread.start();
    }

    private static String TAG = "Permission";
    private static final int RECORD_REQUEST_CODE = 101;





    //------------------------------------------------------------------------------------------
    //MICROPHONE PERMISSION HANDLING
    private void setupPermissions() {

        int permission = ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO);

        if(permission != PackageManager.PERMISSION_GRANTED){
            Log.i(TAG, "Permission to record denied");
            makeRequest();
        }
    }

    protected void makeRequest() {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.RECORD_AUDIO},RECORD_REQUEST_CODE);
    }

    public void onRequestPermissionResult(int requestCode, String permissions[], int[] grantResults){
        switch(requestCode) {
            case RECORD_REQUEST_CODE:
                if(grantResults.length ==0||grantResults[0]!=PackageManager.PERMISSION_GRANTED) {
                    Log.i(TAG, "Permission has been denied by user");
                    //TODO create dialog box warning user that the app cannot function without microphone access
                }
                else {
                    Log.i(TAG, "Permission has been granted by user");

            }
        }
    }
    //-------------------------------------------------------------------------------------------------

    //this method is triggered by the testAudio button
    public void testAudio(View view) {
        //Toast.makeText(this,"Testing Recording", Toast.LENGTH_SHORT).show();
        Bundle extras = getIntent().getExtras();
        int settings_time = extras.getInt("deafen_time"); //gets the time preference of the user from the settings
        int settings_volume = extras.getInt("reduced_vol");
        boolean state = extras.getBoolean("state");

        Intent intent = new Intent(this, RecordingService.class);
        intent.putExtra("state", state);
        intent.putExtra("reduced_vol", settings_volume);
        intent.putExtra("deafen_time", settings_time);
        startService(intent);

    }


    public void stopRecording(View view){
        stopService(new Intent(this,RecordingService.class));
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
        //Toast.makeText(this,String.valueOf(RecordingService.getDeafenFlag()), Toast.LENGTH_SHORT).show();
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