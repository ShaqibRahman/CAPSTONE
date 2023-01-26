package com.example.deafen_prototype;

import android.Manifest;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;

public class RecordingService extends Service {

    private static int DEAFEN_FLAG = 0;
    private static int THRESHOLD = 127;

    @Override
    //service starts on this method
    public int onStartCommand(Intent intent, int flags, int startId) {
        //any start up sequence required goes here
        //Toast.makeText(this,"Recording Service Initialized", Toast.LENGTH_SHORT).show();
        startRecording();
        return START_STICKY;
    }

    @Override
    //delete
    public void onDestroy() {
        super.onDestroy();
    }

    //no idea
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    //AUDIO RECORDING
    //recording parameters
    private static final int SAMPLE_RATE = 4000; //this is something to play with to see if increased sample rate really changes functionality at all, or alternatively what the lowest possible rate is
    private static final int CHANNELS = AudioFormat.CHANNEL_IN_MONO;
    private static final int AUDIO_ENCODING = AudioFormat.ENCODING_PCM_16BIT; //PCM 16 bit is the same as wav file, although wav samples at 44.1kHz

    private AudioRecord recorder = null;
    private Thread recordingThread = null;
    private boolean isRecording = false;

    int bufferSize = AudioRecord.getMinBufferSize(SAMPLE_RATE, CHANNELS, AUDIO_ENCODING);

    int BufferElements2Rec = 1024;
    int BytesPerElement = 2;


    //maybe need to check and initialize microphones

    // do we want to use short or byte??
    private void recordAudio() {
        //Toast.makeText(this,"processAudio() started", Toast.LENGTH_SHORT).show();
        //Toast.makeText(this, "Audio Detected", Toast.LENGTH_SHORT).show();
        short data[] = new short[BufferElements2Rec];
        int x = 0;

        while (isRecording) {
            recorder.read(data, 0, BufferElements2Rec);
            processAudio(data);

        }
    }

    private void processAudio(short[] data){
        short max = shortMax(data);
        if(max>THRESHOLD){
            DEAFEN_FLAG  = 1;
        }
        else{
            DEAFEN_FLAG = 0;
        }
    }

    private void startRecording() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        //try changing to MediaRecorder.AudioSource.UNPROCESSED

        recorder = new AudioRecord(MediaRecorder.AudioSource.UNPROCESSED, SAMPLE_RATE, CHANNELS, AUDIO_ENCODING, BufferElements2Rec * BytesPerElement);

        isRecording = true;
        Toast.makeText(this,"Recording Thread Starting", Toast.LENGTH_SHORT).show();
        Log.i("recording","Recording Thread Starting");
        recordingThread = new Thread(new Runnable() {
            public void run() {
                recorder.startRecording();

                //Log.d("Recording", "Recording thread initiated");
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

    private short shortMax(short[] data){
        short max = 0;
        for (int i = 0; i < data.length; i++) {
            if(data[i]>max){
                max = data[i];
            }
        }
        return max;
    }

    public static int getDeafenFlag(){
        return DEAFEN_FLAG;
    }
}

