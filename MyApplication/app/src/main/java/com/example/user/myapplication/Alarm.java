package com.example.user.myapplication;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;


/**
 * Created by User on 3/10/2016.
 */
public class Alarm extends AppCompatActivity {

    ProgressBar progressBar;
    TextView textCounter, shallbe;
    MyCountDownTimer myCountDownTimer;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarm);

        Global g = (Global) getApplication();

        if (g.getBusNum() != null) {
            String text = g.getBusNum() + " will arrive at";
            TextView title = (TextView) findViewById(R.id.textView7);
            title.setText(text);
        } else {
            String text = g.getBusNum() + " will arrive at";
            TextView title = (TextView) findViewById(R.id.textView7);
            title.setText(text);
        }

        String text2 = g.getStopName() + " in:";
        TextView title2 = (TextView) findViewById(R.id.textView8);
        title2.setText(text2);

        Calendar c = Calendar.getInstance();

        int Hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        int second = c.get(Calendar.SECOND);

        String currentTime = String.valueOf(Hour) + ":" + String.valueOf(minute) + ":" + String.valueOf(second);
        Date now = new Date();

        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        try {
            Date date = format.parse(currentTime);
            now = date;
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        double walking_to_bus = g.getWalking_to_bus()*60*1000;
        long mili_walking_to_bus =  Math.round(walking_to_bus);


        long diffInMillies = getDateDiff(now, g.getArriveTime(), TimeUnit.SECONDS);

        progressBar = (ProgressBar) findViewById(R.id.progressbar);
        textCounter = (TextView) findViewById(R.id.counter);

        progressBar.setProgress(100);
        myCountDownTimer = new MyCountDownTimer(diffInMillies * 1000, 1000);
        myCountDownTimer.setMili_walking_to_bus(mili_walking_to_bus);
        progressBar.setMax((int) diffInMillies * 1000);
        myCountDownTimer.start();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();

        shallbe = (TextView) findViewById(R.id.shallbe);

        if(g.getData_bus_kind()=="MTS"){
            ImageView img= (ImageView) findViewById(R.id.imageViewalarm);
            img.setImageResource(R.drawable.mts);
        }
        else{
            ImageView img= (ImageView) findViewById(R.id.imageViewalarm);
            img.setImageResource(R.drawable.ucsd);
        }
        addListenerOnButton();
    }

    public static long getDateDiff(Date date1, Date date2, TimeUnit timeUnit) {
        long diffInMillies = date2.getTime() - date1.getTime();
        return timeUnit.convert(diffInMillies, TimeUnit.MILLISECONDS);
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Alarm Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.example.user.myapplication/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Alarm Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.example.user.myapplication/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }

    public class MyCountDownTimer extends CountDownTimer {

        long mili_walking_to_bus;

        public MyCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        public void setMili_walking_to_bus(long d){
            this.mili_walking_to_bus = d;
        }

        @Override
        public void onTick(long millisUntilFinished) {

            long remaintime = millisUntilFinished-mili_walking_to_bus;

            textCounter.setText(String.format("%d min, %d sec",
                    TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished),
                    TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))
            ));

            shallbe.setText("You shall depart in " + String.format("%d min, %d sec",
                    TimeUnit.MILLISECONDS.toMinutes(remaintime),
                    TimeUnit.MILLISECONDS.toSeconds(remaintime) -
                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(remaintime))
            ));

            if(mili_walking_to_bus == 0){
                shallbe.setTextColor(Color.parseColor("#b82347"));
                shallbe.setText("");
            }
            else{
                if (remaintime >= 300000) {
                    if(remaintime == 300000) {
                        new AlertDialog.Builder(Alarm.this)
                                .setTitle("Delete entry")
                                .setMessage("5 min")
                                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        // do nothing
                                    }
                                })
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .show();
                    }
                    shallbe.setTextColor(Color.parseColor("#229399"));
                }
                else if(remaintime < 300000 && remaintime >= 180000) {
                    if(remaintime == 180000) {
                        new AlertDialog.Builder(Alarm.this)
                                .setTitle("Delete entry")
                                .setMessage("3 min")
                                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        // do nothing
                                    }
                                })
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .show();
                    }
                    shallbe.setTextColor(Color.parseColor("#ffaa00"));
                }
                else if(remaintime < 180000 && remaintime >= 0) {
                    if(remaintime == 0) {
                        new AlertDialog.Builder(Alarm.this)
                                .setTitle("Delete entry")
                                .setMessage("GO")
                                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        // do nothing
                                    }
                                })
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .show();
                    }
                    shallbe.setTextColor(Color.parseColor("#b82347"));
                }
                else if(remaintime < 0){
                    shallbe.setTextColor(Color.parseColor("#b82347"));
                    shallbe.setText("You shall have already departed");
                }

            }


            if (millisUntilFinished >= 300000) {
                textCounter.setTextColor(Color.parseColor("#229399"));
            }
            else if(millisUntilFinished < 300000 && millisUntilFinished >= 180000) {
                textCounter.setTextColor(Color.parseColor("#ffaa00"));
            }
            else if(millisUntilFinished < 180000) {
                textCounter.setTextColor(Color.parseColor("#b82347"));
            }
            int progress = (int) (millisUntilFinished);
            progressBar.setProgress(progress);
        }

        @Override
        public void onFinish() {
            textCounter.setText("Bus Arrived.");
            progressBar.setProgress(0);
        }

    }

    public void addListenerOnButton() {
        final Context context = this;
        Button returnbutton = (Button) findViewById(R.id.restart);

        returnbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(context, MainActivity.class);
                startActivity(intent);
            }
        });
    }

}
