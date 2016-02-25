package com.sd_commute;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class WalkActivity extends AppCompatActivity {

    //choose walk speed
    TextView bus_num_ucsd = null;
    Button spd=null;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.walk_ask);

        Global g = (Global)getApplication();
        setListener(g.getData_bus_kind(),g.getData_method());

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

    private void setListener(String bus,String dataMethod) {
        spd=(Button)findViewById(R.id.submit_speed);
        final Context context=this;
        final String busKind=bus;
        final String method=dataMethod;
        spd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                intent = new Intent(context, MapsActivity.class);
                startActivity(intent);
                Global g=(Global)getApplication();
                g.setData_bus_kind(busKind);
                g.setData_method(method);
            }
        });
    }



}
