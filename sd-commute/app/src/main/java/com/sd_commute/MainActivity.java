package com.sd_commute;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    //main activity after splash
    //choose mts or UCSD

    ImageButton imageButton1,imageButton2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addListenerOnButton();
    }

    public void addListenerOnButton() {
        final Context context = this;
        imageButton1 = (ImageButton) findViewById(R.id.imageButton);
        imageButton2 = (ImageButton) findViewById(R.id.imageButton2);

        imageButton1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(context, LocationActivity.class);
                startActivity(intent);
                Global g = (Global)getApplication();
                g.setData_bus_kind("MTS");
            }
        });

        imageButton2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(context, LocationActivity.class);
                startActivity(intent);
                Global g = (Global)getApplication();
                g.setData_bus_kind("UCSD");
            }
        });
    }
}
