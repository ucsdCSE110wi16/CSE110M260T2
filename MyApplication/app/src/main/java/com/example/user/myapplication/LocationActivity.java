package com.example.user.myapplication;

import android.content.Context;
import android.content.Intent;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import java.io.IOException;
import java.util.List;

public class LocationActivity extends AppCompatActivity {
    //google maps key
    //AIzaSyCPM4jHENVWYtUmQ4lxQarUr7_QLWSbHw4

    //choose location based on current or address

    Button auto_button,enter_button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.location_ask);
        addListenerOnButton();

        Global g = (Global)getApplication();


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

        final EditText address= (EditText) findViewById(R.id.Address);
        final EditText dest= (EditText) findViewById(R.id.destination);
        addEditorAction(address);
        addEditorAction(dest);



    }

    public void addEditorAction(EditText e){
        e.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId,
                                          KeyEvent event) {
                if ((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER))
                        || (actionId == EditorInfo.IME_ACTION_DONE)) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
                return false;
            }
        });

    }

    public void addListenerOnButton() {
        final Context context = this;
        auto_button = (Button) findViewById(R.id.auto_ucsd);
        enter_button = (Button) findViewById(R.id.enter_ucsd);

        auto_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                EditText address=(EditText)findViewById(R.id.Address);
            /*Intent intent = new Intent(context, MapsActivity.class);
            startActivity(intent);
            Global g = (Global) getApplication();
            g.setData_method("auto");*/
                address.setText("Your Location");
            }
        });

        //when user inputs an address
        enter_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                EditText address = (EditText) findViewById(R.id.Address);
                EditText destination= (EditText)findViewById(R.id.destination);

                String address_enter = address.getText().toString();
                String dest_address = destination.getText().toString();

                if (address_enter.matches("")||dest_address.matches("")) {
                    Toast.makeText(LocationActivity.this, "You did not enter an address.", Toast.LENGTH_SHORT).show();
                    return;
                }

                Intent intent = new Intent(context, MapsActivity.class);

                Bundle bundle = new Bundle();
                bundle.putString("address_entered", address_enter);
                bundle.putString("destination",dest_address);
                intent.putExtras(bundle);


                startActivity(intent);
                Global g = (Global) getApplication();
                g.setData_method("address");
            }
        });
    }
}
