package com.example.deafen_prototype;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;

public class SettingsActivity extends AppCompatActivity {

    private ImageButton backbutton;
    private SeekBar volume;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        backbutton = (ImageButton) findViewById(R.id.BackButton);
        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenMain();
            }
        });

        volume = (SeekBar) findViewById(R.id.Pause_Mute_Deafen_bar);
        volume.setMax(2);
        volume.setProgress(1);
    }

    public void OpenMain(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}