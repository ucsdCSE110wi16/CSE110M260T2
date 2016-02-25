package com.example.user.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;


public class WalkActivity extends AppCompatActivity {

    private TextView speedText;
    private SeekBar speed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.walk_ask);

        Global g = (Global) getApplication();
        speed = (SeekBar) findViewById(R.id.seekBar);
        speedText = (TextView) findViewById(R.id.speedText);

        speedText.setText(speed.getProgress() + "/" + speed.getMax());
        speed.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            int progress = 0;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progresValue, boolean fromUser) {
                progress = progresValue;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // Do something here,
                //if you want to do anything at the start of
                // touching the seekbar
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // Display the value in textview
                speedText.setText(progress + "/" + seekBar.getMax());
            }
        });

        if(g.getData_bus_kind()=="MTS"){
            String text= "MTS";
            TextView title = (TextView)findViewById(R.id.ask_bus_type);
            title.setText(text);
            ImageView img= (ImageView) findViewById(R.id.location_choice);
            img.setImageResource(R.drawable.mts);
        }
        else{
            String text= "UCSD";
            TextView title = (TextView)findViewById(R.id.ask_bus_type);
            title.setText(text);
            ImageView img= (ImageView) findViewById(R.id.location_choice);
            img.setImageResource(R.drawable.ucsd);
        }
    }
}