package com.example.user.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class UCSDActivity extends Activity {

    Button auto_button,enter_button;
    TextView bus_type = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ucsd_ask);
        addListenerOnButton();

        Global g = (Global)getApplication();

        if(g.getData_bus_kind()=="MTS"){
            String text= "MTS";
            TextView title = (TextView)findViewById(R.id.ask_bus_type);
            title.setText(text);
        }
        else{
            String text= "UCSD";
            TextView title = (TextView)findViewById(R.id.ask_bus_type);
            title.setText(text);
        }

    }

    public void addListenerOnButton() {
        final Context context = this;
        auto_button = (Button) findViewById(R.id.auto_ucsd);
        enter_button = (Button) findViewById(R.id.enter_ucsd);

        auto_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(context, MAPActivity.class);
                startActivity(intent);
                Global g = (Global)getApplication();
                g.setData_method("auto");
            }
        });

        enter_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                EditText bus_number = (EditText) findViewById(R.id.UCSD_bus_stop);
                String ucsd_bus_num = bus_number.getText().toString();

                Intent intent = new Intent(context, MAPActivity.class);

                Bundle bundle = new Bundle();
                bundle.putString("UCSD_BUS_NUM", ucsd_bus_num);
                intent.putExtras(bundle);

                startActivity(intent);
                Global g = (Global)getApplication();
                g.setData_method("bus_stop");
            }
        });
    }
}
