package com.example.user.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class LocationActivity extends AppCompatActivity {
    //google maps key
    //AIzaSyCPM4jHENVWYtUmQ4lxQarUr7_QLWSbHw4

    //choose location based on current or address

    Button auto_button,enter_button;
    TextView bus_type = null;

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

    }

    public void addListenerOnButton() {
        final Context context = this;
        auto_button = (Button) findViewById(R.id.auto_ucsd);
        enter_button = (Button) findViewById(R.id.enter_ucsd);

        auto_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(context, WalkActivity.class);
                startActivity(intent);
                Global g = (Global)getApplication();
                g.setData_method("auto");
            }
        });

        enter_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                EditText address = (EditText) findViewById(R.id.Address);
                String address_enter = address.getText().toString();

                if (address_enter.matches("")) {
                    Toast.makeText(LocationActivity.this, "You did not enter an address.", Toast.LENGTH_SHORT).show();
                    return;
                }


                Intent intent = new Intent(context, WalkActivity.class);

                Bundle bundle = new Bundle();
                bundle.putString("UCSD_BUS_NUM", address_enter);
                intent.putExtras(bundle);

                startActivity(intent);
                Global g = (Global) getApplication();
                g.setData_method("address");
            }
        });
    }
}
