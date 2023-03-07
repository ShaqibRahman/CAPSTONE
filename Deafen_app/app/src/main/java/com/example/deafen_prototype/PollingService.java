package com.example.deafen_prototype;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.AudioRecord;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class PollingService extends Service {
    public PollingService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){


        return START_STICKY;
    }

    private Thread pollingThread;

    public void startPolling(){
        pollingThread = new Thread(new Runnable() {
            public void run() {
                try {
                    pollFlag();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


            }
        }, "AudioRecorder Thread");
        pollingThread.start();
    }

    public void pollFlag() throws InterruptedException {
        int flag = RecordingService.getDeafenFlag();

        if(flag==1){
             flagAction();

        }
        else{

        }

        pollingThread.sleep(200);
    }

    public void flagAction(){

    }


}