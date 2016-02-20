package com.example.user.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class UCSDActivity extends Activity {

    Button auto_button,enter_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ucsd_ask);


    }

    public void addListenerOnButton() {
        final Context context = this;
        auto_button = (Button) findViewById(R.id.auto_ucsd);
        enter_button = (Button) findViewById(R.id.enter_ucsd);

        auto_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(context, MTSActivity.class);
                startActivity(intent);
            }
        });

        enter_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                EditText bus_number = (EditText) findViewById(R.id.textView4);
                Intent intent = new Intent(context, UCSDActivity.class);
                startActivity(intent);
            }
        });
    }
}
