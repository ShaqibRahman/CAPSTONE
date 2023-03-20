package com.example.deafen_prototype;

import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

public class RecordingService extends Service {



    //the following parameters can modify the performance of the app
    private static final int SAMPLE_RATE = 8000;
    private static int THRESHOLD = 200;
    int BufferElements2Rec = 1024;
    int BytesPerElement = 2;

    //other necessary initializations
    private static int DEAFEN_FLAG = 0;
    private static int user_volume=0;
    private static int current_volume = 0;
    AudioManager audioManager;
    private static final int CHANNELS = AudioFormat.CHANNEL_IN_MONO;
    private static final int AUDIO_ENCODING = AudioFormat.ENCODING_PCM_16BIT; //PCM 16 bit is the same as wav file, although wav samples at 44.1kHz

    private AudioRecord recorder = null;
    private Thread recordingThread = null;
    private boolean isRecording = false;

    int bufferSize = AudioRecord.getMinBufferSize(SAMPLE_RATE, CHANNELS, AUDIO_ENCODING);


    @Override
    //service starts on this method
    public int onStartCommand(Intent intent, int flags, int startId) {

        Log.i("recording","recording service initialized");

        audioManager = (AudioManager) getApplicationContext().getSystemService(Context.AUDIO_SERVICE);

        //this is to protect from the case where settings have not been changed.
        if(!intent.getExtras().equals(null)) {
            THRESHOLD = 16 * intent.getExtras().getInt("threshold");
        }
        //remains default otherwise

        Log.i("parameter","threshold = "+ THRESHOLD);


        startRecording();
        return START_STICKY;
    }


    //initializes the recording thread
    private void startRecording() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            //TODO implement a no permission granted display
            return;
        }
        //try changing to MediaRecorder.AudioSource.UNPROCESSED

        //initialize recorder
        recorder = new AudioRecord(MediaRecorder.AudioSource.UNPROCESSED, SAMPLE_RATE, CHANNELS, AUDIO_ENCODING, BufferElements2Rec * BytesPerElement);
        user_volume=audioManager.getStreamVolume(AudioManager.STREAM_MUSIC); //get the current volume



        isRecording = true;
        Toast.makeText(this,"Recording Thread Starting", Toast.LENGTH_SHORT).show();
        Log.i("recording","Recording Thread Starting");
        recordingThread = new Thread(new Runnable() {
            public void run() {
                recorder.startRecording();

                if(recorder.getRecordingState()==AudioRecord.RECORDSTATE_RECORDING){
                    Log.i("recording","AudioRecord Recording");
                    recordAudio();
                }
                else{
                    Log.i("recording","AudioRecord not recording");
                }
            }
        }, "AudioRecorder Thread");
        recordingThread.start();
    }

    //maybe need to check and initialize microphones

    // do we want to use short or byte??
    private void recordAudio() {
        //Toast.makeText(this,"processAudio() started", Toast.LENGTH_SHORT).show();
        //Toast.makeText(this, "Audio Detected", Toast.LENGTH_SHORT).show();
        short data[] = new short[BufferElements2Rec];
        int x = 0;
        user_volume=audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        while (isRecording) {
            recorder.read(data, 0, BufferElements2Rec);

            processAudio(data);

        }
    }


    private void processAudio(short[] data){ //edit this function to change audio functionality
        short max = shortMax(data);

        //int old_volume = audioManager.getStreamVolume(AudioManager.STREAM_SYSTEM);

        if(max>THRESHOLD){
            if(DEAFEN_FLAG ==0){
                DEAFEN_FLAG = 1;
                flagOneAction();
            }
        }
        else{
            DEAFEN_FLAG=0;

            flagZeroAction();//this is outside

        }
    }


    private void flagZeroAction(){
        if(current_volume<user_volume){ //this creates the gradual increase
            current_volume++;
        }
        //this is triggered when the flag goes from 1 to 0

        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,current_volume,AudioManager.FLAG_SHOW_UI);
    }

    private void flagOneAction(){


        current_volume=0;
        //this is triggered when the flag goes from 0 to 1
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,current_volume,AudioManager.FLAG_SHOW_UI);
    }

    private short shortMax(short[] data){
        short max = 0;
        for (int i = 0; i < data.length; i++) {
            if(data[i]>max){
                max = data[i];
            }
        }
        return max;
    }


//  Utility functions ------------------------------------------------------------------------------

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    //delete
    public void onDestroy() {
        isRecording=false;
        recorder.release();
        super.onDestroy();
    }

    private void endRecording(){ //make this better?
        isRecording=false;
        recorder.release();

    }



    public static int getDeafenFlag(){
        return DEAFEN_FLAG;
    }



    //this is useless and can be removed
    /*
    public void DeafenTrigger(){


        //getMainLooper().prepare();
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Do something after 3s = 3000ms
                for(int i = 0;i<100;i++){
                    Log.i("deafening", "deafening");
                }
            }
        }, 1000);
    }
    */

}

