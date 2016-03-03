package com.example.user.myapplication;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * Created by EC on 3/3/2016.
 */
class MtsStop {
    private double lat,lon;
    private String id,name;
    public ArrayList<Route> routes;

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
        }else if (o instanceof String) {
            String x=(String)o;
            if (x.equals(id)){
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
        return id.hashCode();
    }

    public String getID(){
        return id;
    }

    public Route getRoute(Route r){
        return (Route)routes.get(routes.indexOf(r));
    }
    public Route getRoute(String r){
        return (Route)routes.get(routes.indexOf(r));
    }

    public boolean contains(Route r){
        return routes.contains(r);
    }
    public boolean contains(String r){
        return routes.contains(r);
    }

}// end stop


class Route{
    private Queue<String> timeSort;
    private List<String> times;
    String id;
    public Route(String id){
        this.id=id;
        timeSort=new PriorityQueue<>();
        times=new ArrayList<String>();
    }


    public void addTime(String time){
        timeSort.add(time);
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
        }else if(o instanceof String) {
            String x=(String)o;
            if(id.equals(o)) {
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
        return id.hashCode();
    }

    public String getID(){
        return id;
    }


    public List getTimesList(){
        return times;
    }
    public void finalizeList(){
        for(String s:timeSort){
            times.add(timeSort.poll());
        }
    }

}//end route

