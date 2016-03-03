package com.example.user.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;


public class WalkActivity extends AppCompatActivity {

    //choose walk speed
    Button spd=null;
    Intent intent;
    private TextView speedText;
    private SeekBar speed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.walk_ask);

        Global g = (Global)getApplication();
        setListener(g.getData_bus_kind(),g.getData_method());
        speed = (SeekBar) findViewById(R.id.seekBar);
        speedText = (TextView) findViewById(R.id.speedText);

        speedText.setText(speed.getProgress() + "/" + speed.getMax());
        speed.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            int progress = 0;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progresValue, boolean fromUser) {
                progress = progresValue;
                //changes as user swipes
                speedText.setText(progress + "/" + seekBar.getMax());
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

    private void setListener(String bus,String dataMethod) {
        spd=(Button)findViewById(R.id.submit_speed);
        final Context context=this;
        final String busKind=bus;
        final String method=dataMethod;
        spd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                intent = new Intent(context, LocationActivity.class);
                startActivity(intent);
                Global g=(Global)getApplication();
                g.setData_bus_kind(busKind);
                g.setData_method(method);
            }
        });
    }



}

