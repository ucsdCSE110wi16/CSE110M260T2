package com.example.user.myapplication;

import android.app.Application;

import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Date;

/**
 * Created by User on 2/20/2016.
 */
public class Global extends Application {
    private String bus_kind = "1";
    private String method = "2";
    private MarkerOptions start=null;
    private MarkerOptions end=null;

    private double walking_to_bus=0;
    private double walking_speed=5;

    private String busnum;
    private String stopname;

    private Date arrivetime;

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
    public double getWalking_to_bus(){
        return walking_to_bus*((15-walking_speed)/10);
    }
    public void setWalking_to_bus(int d){
        this.walking_to_bus=d;
    }

    //walking speed
    public void setWalking_speed(int d){
        this.walking_speed=d;
    }
    public double getWalking_speed(){
        return walking_speed;
    }

    //Bus number
    public void setBusNum(String d){
        this.busnum=d;
    }
    public String getBusNum(){
        return busnum;
    }

    //Stop name
    public void setStopName(String d){
        this.stopname=d;
    }
    public String getStopName(){
        return stopname;
    }

    //Arriving time
    public void setArriveTime(Date d){
        this.arrivetime=d;
    }
    public Date getArriveTime(){
        return arrivetime;
    }
}