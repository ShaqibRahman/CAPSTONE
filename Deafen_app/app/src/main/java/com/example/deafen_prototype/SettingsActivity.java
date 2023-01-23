package com.example.deafen_prototype;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.Toast;

public class SettingsActivity extends AppCompatActivity {

    private ImageButton backbutton;
    public SeekBar volume;
    public SeekBar time;
    private Switch action;
    public int new_volume;
    public int new_time;

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
        volume.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progressChangedValue = 5;

            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressChangedValue = progress;
                new_volume = progress;

            }

            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Toast.makeText(SettingsActivity.this, "Volume bar progress is :" + progressChangedValue,
                        Toast.LENGTH_SHORT).show();
            }
        });

        time = (SeekBar) findViewById(R.id.timeBar);
        time.setMax(10);

        time.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int timeChangedValue = 5;

            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                timeChangedValue = progress;
                new_time = progress;

            }

            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Toast.makeText(SettingsActivity.this, "Time bar progress is :" + timeChangedValue,
                        Toast.LENGTH_SHORT).show();
            }
        });

        action = (Switch)  findViewById(R.id.action_switch);

    }

    public void OpenMain(){
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("state", CheckAction());
        intent.putExtra("reduced_vol", new_volume);
        intent.putExtra("deafen_time", new_time);
        startActivity(intent);
    }

    public boolean CheckAction(){

        return action.isChecked();
    }
}