package com.example.user.myapplication;

import android.app.Application;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by User on 2/20/2016.
 */
public class Global extends Application {
    private String bus_kind = "1";
    private String method = "2";
    private MarkerOptions x=null;
    private String arrival_time=null;
    private int walking_to_bus=0;

    public void setMarker(MarkerOptions m) {x = m;}

    public MarkerOptions getMarker() {return x;}

    public String getData_bus_kind(){
        return bus_kind;
    }

    public void setData_bus_kind(String d){
        this.bus_kind=d;
    }
    public String getArrival_time(){
        return arrival_time;
    }

    public void setArrival_time(String d){
        this.arrival_time=d;
    }

    public String getData_method(){
        return method;
    }

    public void setData_method(String d){
        this.method=d;
    }

    public int getWalking_to_bus(){
        return walking_to_bus;
    }

    public void setWalking_to_bus(int d){
        this.walking_to_bus=d;
    }
}