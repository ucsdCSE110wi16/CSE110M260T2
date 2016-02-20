package com.example.user.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MAPActivity extends Activity {

    TextView bus_num_ucsd = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map);

        Global g = (Global)getApplication();

        if(g.getData_method()=="bus_stop"){
            Bundle bundle = getIntent().getExtras();
            String text= bundle.getString("UCSD_BUS_NUM");

            LinearLayout lView = (LinearLayout)findViewById(R.id.map_layout);

            bus_num_ucsd = new TextView(this);
            bus_num_ucsd.setText(text);

            lView.addView(bus_num_ucsd);
        }
        else{
            String text= "123";
            LinearLayout lView = (LinearLayout)findViewById(R.id.map_layout);

            bus_num_ucsd = new TextView(this);
            bus_num_ucsd.setText(text);

            lView.addView(bus_num_ucsd);
        }
    }
}
