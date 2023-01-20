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
    private Switch action;
    public int new_volume;

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
                Toast.makeText(SettingsActivity.this, "Seek bar progress is :" + progressChangedValue,
                        Toast.LENGTH_SHORT).show();
            }
        });

        action = (Switch)  findViewById(R.id.action_switch);
        action.setChecked(true);

    }

    public void OpenMain(){
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("state", CheckAction());
        intent.putExtra("reduced_vol", new_volume);
        startActivity(intent);
    }

    public boolean CheckAction(){

        return action.isChecked();
    }
}