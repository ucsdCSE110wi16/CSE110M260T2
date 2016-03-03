package com.example.user.myapplication;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by EC on 3/3/2016.
 */
class MtsStop {
    private double lat,lon;
    private String id,name;
    private ArrayList<Route> routes;

    public MtsStop(double lat, double lon,String id,String name){
        this.lat=lat;
        this.lon=lon;
        this.id=id;
        this.name=name;
        routes=new ArrayList<Route>();
    }

    public MtsStop(String id){
        this.id=id;
    }
    public String string(){
        return(lat+","+lon+","+id+","+name);
    }

    public Route addRoute(Route r){
        if(routes.indexOf(r)!=-1){
            return (Route)routes.get(routes.indexOf(r));

        }else{
            routes.add(r);
            return r;
        }
    }


    @Override
    public boolean equals(Object o){
        if (o instanceof MtsStop){
            MtsStop x=(MtsStop)o;
            if (x.getID().equals(id)){
                return true;
            }else{
                return false;
            }
        }else{
            return false;
        }
    }
    @Override
    public int hashCode(){
        return Integer.parseInt(id);
    }

    public String getID(){
        return id;
    }

    public Route findR(Route r){
        return (Route)routes.get(routes.indexOf(r));
    }

}// end stop


class Route{
    private ArrayList<String> times;
    String id;
    public Route(String id){
        this.id=id;
        times=new ArrayList<String>();
    }


    public void addTime(String time){
        times.add(time);
    }

    public Route clone(){
        return new Route(id);
    }

    @Override
    public boolean equals(Object o){
        if (o instanceof Route){
            Route x=(Route)o;
            if (x.getID().equals(id)){
                return true;
            }else{
                return false;
            }
        }else{
            return false;
        }
    }

    @Override
    public int hashCode(){
        return Integer.parseInt(id);
    }

    public String getID(){
        return id;
    }


    public ArrayList getTimesList(){
        return times;
    }

}//end route
