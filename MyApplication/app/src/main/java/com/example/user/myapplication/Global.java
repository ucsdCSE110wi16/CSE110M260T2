package com.example.user.myapplication;

import android.app.Application;

/**
 * Created by User on 2/20/2016.
 */
public class Global extends Application {
    private String bus_kind = "1";
    private String method = "2";

    public String getData_bus_kind(){
        return bus_kind;
    }

    public void setData_bus_kind(String d){
        this.bus_kind=d;
    }

    public String getData_method(){
        return method;
    }

    public void setData_method(String d){
        this.method=d;
    }

}