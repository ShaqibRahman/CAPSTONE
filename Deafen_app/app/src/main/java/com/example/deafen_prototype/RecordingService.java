package com.example.deafen_prototype;

import android.Manifest;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.IBinder;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;

public class RecordingService extends Service {

    @Override
    //service starts on this method
    public int onStartCommand(Intent intent, int flags, int startId) {
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
    private static final int SAMPLE_RATE = 8000;
    private static final int CHANNELS = AudioFormat.CHANNEL_IN_MONO;
    private static final int AUDIO_ENCODING = AudioFormat.ENCODING_PCM_16BIT;

    private AudioRecord recorder = null;
    private Thread recordingThread = null;
    private boolean isRecording = false;

    int bufferSize = AudioRecord.getMinBufferSize(SAMPLE_RATE, CHANNELS, AUDIO_ENCODING);

    int BufferElements2Rec = 1024;
    int BytesPerElement = 2;


    //gonna want to make the recording a separate <service>
    public void testAudio(View view) {
        //Toast.makeText(this,"Testing Recording", Toast.LENGTH_SHORT).show();
        startRecording();
    }


    //maybe need to check and initialize microphones

    // do we want to use short or byte??
    private void processAudio() {
        //Toast.makeText(this, "Audio Detected", Toast.LENGTH_SHORT).show();
        byte data[] = new byte[BufferElements2Rec];
        int x = 0;
        while (isRecording) {
            recorder.read(data, 0, BufferElements2Rec);
            int i = data[100];
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
        recorder = new AudioRecord(MediaRecorder.AudioSource.MIC, SAMPLE_RATE, CHANNELS, AUDIO_ENCODING, BufferElements2Rec * BytesPerElement);

        isRecording = true;
        Toast.makeText(this,"Recording Thread Starting", Toast.LENGTH_SHORT).show();

        recordingThread = new Thread(new Runnable() {
            public void run() {

                //Log.d("Recording", "Recording thread initiated");
                processAudio();

            }
        }, "AudioRecorder Thread");
        recordingThread.start();


    }
}

