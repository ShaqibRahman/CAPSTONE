package com.example.deafen_prototype;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.Switch;

public class SettingsActivity extends AppCompatActivity {

    private ImageButton backbutton;
    private SeekBar volume;
    private Switch action;

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

        volume = (SeekBar) findViewById(R.id.Deafen_volume);
        volume.setMax(15);
        volume.setProgress(5);

        action = (Switch)  findViewById(R.id.action_switch);
        action.setChecked(true);

    }

    public void OpenMain(){
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("state", CheckAction());
        startActivity(intent);
    }

    public boolean CheckAction(){

        return action.isChecked();
    }

}