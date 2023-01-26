package com.example.deafen_prototype;

import androidx.appcompat.app.AppCompatActivity;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;import android.app.Activity;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

public class RecordingActivity extends AppCompatActivity {
    //borrowed code from Rahul Baradia http://audiorecordandroid.blogspot.com/

    private static final int SAMPLE_RATE = 8000;
    private static final int CHANNELS = AudioFormat.CHANNEL_IN_MONO;
    private static final int AUDIO_ENCODING = AudioFormat.ENCODING_PCM_16BIT;

    private AudioRecord recorder = null;
    private Thread recordingThread = null;
    private boolean isRecording = false;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);



    }

    int BufferElements2Rec = 1024;
    int BytesPerElement = 2;

    private void startRecording() {

        //need to figure out permission
        recorder = new AudioRecord(MediaRecorder.AudioSource.MIC,
                SAMPLE_RATE,CHANNELS,AUDIO_ENCODING,BufferElements2Rec*BytesPerElement );

        recorder.startRecording();

        isRecording=true;

        recordingThread = new Thread(new Runnable(){
            public void run(){
                processAudio();
            }
        }

    }



}
