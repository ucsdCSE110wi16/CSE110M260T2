package com.example.user.myapplication;

import android.app.Application;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by User on 2/20/2016.
 */
public class Global extends Application {
    private String bus_kind = "1";
    private String method = "2";
    private MarkerOptions start=null;
    private MarkerOptions end=null;

    private int walking_to_bus=0;

    //where user gets on bus
    public void setStartMarker(MarkerOptions m) {start = m;}
    public MarkerOptions getStartMarker() {return start;}

    //end marker for bus dest
    public void setEndMarker(MarkerOptions m) {end = m;}
    public MarkerOptions getEndMarker() {return end;}

    //which bus, ucsd or mts
    public String getData_bus_kind(){
        return bus_kind;
    }
    public void setData_bus_kind(String d){
        this.bus_kind=d;
    }

    //what method we use to find stop
    public String getData_method(){
        return method;
    }
    public void setData_method(String d){
        this.method=d;
    }

    //time to walk to bus stop
    public int getWalking_to_bus(){
        return walking_to_bus;
    }
    public void setWalking_to_bus(int d){
        this.walking_to_bus=d;
    }
}