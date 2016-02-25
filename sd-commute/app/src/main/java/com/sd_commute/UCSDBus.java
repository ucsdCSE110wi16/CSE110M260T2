package com.sd_commute;

import android.util.JsonReader;

import org.json.*;
import java.util.Date;
import java.util.ArrayList;

/**
 * Created by Elyas on 2/25/2016.
 */

class Coordinate
{
    public double latitude;
    public double longitude;
}

enum BusDirection
{
    N, NE, E, SE, S, SW, W, NW
}

class UCSDVehicle
{
    int busID;
    double APCPercentage;
    int routeID;
    int patternID;
    int nameNum;
    boolean hasAPC;
    String iconPrefix;
    int doorStatus;
    Coordinate coordinate;
    int speed;
    BusDirection heading;
    Date lastUpdated;
}

class UCSDBusStop
{
    int stopID;
    Coordinate coordinate;
    String stopName;
    int rtpiNumber;
}

class UCSDBusRoute
{
    private int routeID;
    private String name;
    private ArrayList<UCSDBusStop> stops;
    private ArrayList<UCSDVehicle> vehicles;
    private ArrayList<Coordinate> path;

    // constructor
    public UCSDBusRoute(int routeID, String name)
    {
        this.routeID = routeID;
        this.name = name;
        // JsonReader stopsParser = new JsonReader();
        // JsonReader vehiclesParser = new JsonReader();
        // JsonReader pathParser = new JsonReader();
    }

    public int getRouteID()
    {
        return routeID;
    }

    public String getName()
    {
        return name;
    }
}

public class UCSDBus
{
    UCSDBusRoute[] routes;
    final int totalNumStops = 15;

    public UCSDBus()
    {
        routes = new UCSDBusRoute[totalNumStops];

        routes[0] = new UCSDBusRoute(3168, "(A)(N) City Shuttle (Arriba/Nobel)");
        routes[1] = new UCSDBusRoute(0312, "(C) Coaster East");
        routes[2] = new UCSDBusRoute(2092, "(C)(W) Coaster East/West (mid-day)");
        routes[3] = new UCSDBusRoute(0314, "(CP) Chancellor's Park");
        routes[4] = new UCSDBusRoute(1114, "(H) Hillcrest/Campus A.M.");
        routes[5] = new UCSDBusRoute(1264, "(H) Hillcrest/Campus P.M.");
        routes[6] = new UCSDBusRoute(3442, "(L) Clockwise Campus Loop - Peterson Hall to Torrey Pines Center");
        routes[7] = new UCSDBusRoute(1263, "(L) Counter Campus Loop - Torrey Pines Center to Peterson Hall");
        routes[8] = new UCSDBusRoute(1113, "(L) Evening / Weekend Clockwise Campus Loop");
        routes[9] = new UCSDBusRoute(3159, "(L) Evening / Weekend Counter Clockwise Campus Loop");
        routes[10] = new UCSDBusRoute(3440, "(M) Mesa");
        routes[11] = new UCSDBusRoute(1098, "(P) Regents");
        routes[12] = new UCSDBusRoute(2399, "(S) SIO Loop");
        routes[13] = new UCSDBusRoute(1434, "(SC) Sanford Consortium Shuttle");
        routes[14] = new UCSDBusRoute(0313, "(W) Coaster West");

    }

}
