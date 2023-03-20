package com.example.deafen_prototype;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
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
        SharedPreferences sharedPreferences=getSharedPreferences("save",MODE_PRIVATE);

        backbutton = (ImageButton) findViewById(R.id.BackButton);
        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                OpenMain();
            }
        });

        volume = (SeekBar) findViewById(R.id.Deafen_volume);
        volume.setMax(15);
        volume.setProgress(sharedPreferences.getInt("volume", 0));

        new_volume = volume.getProgress();

        volume.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                SharedPreferences.Editor editor=getSharedPreferences("save",MODE_PRIVATE).edit();
                editor.putInt("volume", progress);
                editor.apply();
                volume.setProgress(progress);
                new_volume = progress;

            }

            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Toast.makeText(SettingsActivity.this, "Volume bar progress is :" + new_volume,
                        Toast.LENGTH_SHORT).show();
            }
        });

        time = (SeekBar) findViewById(R.id.timeBar);
        time.setMax(10);
        time.setProgress(sharedPreferences.getInt("time", 0));

        new_time = time.getProgress();

        time.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                SharedPreferences.Editor editor=getSharedPreferences("save",MODE_PRIVATE).edit();
                editor.putInt("time", progress);
                editor.apply();
                time.setProgress(progress);
                new_time = progress;

            }

            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Toast.makeText(SettingsActivity.this, "Time bar progress is :" + new_time,
                        Toast.LENGTH_SHORT).show();
            }
        });

        action = (Switch)  findViewById(R.id.action_switch);

        action.setChecked(sharedPreferences.getBoolean("value",true));

        action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (action.isChecked())
                {
                    // When switch checked
                    SharedPreferences.Editor editor=getSharedPreferences("save",MODE_PRIVATE).edit();
                    editor.putBoolean("value",true);
                    editor.apply();
                    action.setChecked(true);
                }
                else
                {
                    // When switch unchecked
                    SharedPreferences.Editor editor=getSharedPreferences("save",MODE_PRIVATE).edit();
                    editor.putBoolean("value",false);
                    editor.apply();
                    action.setChecked(false);
                }
            }
        });

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {

        outState.putInt("volume", new_volume);
        outState.putInt("time", new_time);
        outState.putBoolean("state", CheckAction());
        super.onSaveInstanceState(outState);

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