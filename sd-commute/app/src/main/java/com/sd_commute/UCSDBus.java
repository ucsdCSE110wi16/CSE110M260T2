package com.sd_commute;

import android.os.AsyncTask;
import android.util.Log;
import org.json.*;
import java.util.Date;
import java.util.ArrayList;

/**
 * Created by Elyas on 2/25/2016.
 */

public class UCSDBus extends AsyncTask<String, Void, String>
{

    JSONHtmlParser htmlParser;

    class Coordinate
    {
        public double latitude;
        public double longitude;
        public Coordinate (double latitude, double longitude)
        {
            this.latitude = latitude;
            this.longitude = longitude;
        }
    }

    enum BusDirection
    {
        N, NE, E, SE, S, SW, W, NW
    }

    class UCSDVehicle
    {
        public int getBusID() {
            return busID;
        }

        public int getRouteID() {
            return routeID;
        }

        public int getPatternID() {
            return patternID;
        }

        public int getNameNum() {
            return nameNum;
        }

        public boolean isHasAPC() {
            return hasAPC;
        }

        public int getDoorStatus() {
            return doorStatus;
        }

        public Coordinate getCoordinate() {
            return coordinate;
        }

        public int getSpeed() {
            return speed;
        }

        public BusDirection getHeading() {
            return heading;
        }

        public Date getLastUpdated() {
            return lastUpdated;
        }

        private int busID;
        private int routeID;
        private int patternID;
        private int nameNum;
        private boolean hasAPC;
        private int doorStatus;
        private Coordinate coordinate;
        private int speed;
        private BusDirection heading;
        private Date lastUpdated;

        public UCSDVehicle(int busID, int routeID, int patternID, int nameNum, boolean hasAPC,
                           int doorStatus, Coordinate coordinate,
                           int speed, BusDirection heading, Date lastUpdated)
        {
            this.busID = busID;
            this.routeID = routeID;
            this.patternID = patternID;
            this.nameNum = nameNum;
            this.hasAPC = hasAPC;
            this.doorStatus = doorStatus;
            this.coordinate = coordinate;
            this.speed = speed;
            this.heading = heading;
            this.lastUpdated = lastUpdated;
        }
    }

    class UCSDBusStop
    {
        private int stopID;
        private Coordinate coordinate;

        public int getStopID()
        {
            return stopID;
        }

        public String getStopName()
        {
            return stopName;
        }

        public int getRtpiNumber()
        {
            return rtpiNumber;
        }

        public Coordinate getCoordinate()
        {
            return coordinate;
        }

        private String stopName;
        private int rtpiNumber;
        public UCSDBusStop(int stopID, Coordinate coordinate,
                           String stopName, int rtpiNumber)
        {
            this.stopID = stopID;
            this.coordinate.latitude = coordinate.latitude;
            this.coordinate.longitude = coordinate.longitude;
            this.stopName = new String(stopName);
            this.rtpiNumber = rtpiNumber;
        }
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
            String stopsJSON = htmlParser.getJSON("http://www.ucsdbus.com/Route/"
                    + new Integer(routeID).toString() + "/Direction/0/Stops");
            String vehiclesJSON = htmlParser.getJSON("http://www.ucsdbus.com/Route/"
                    + new Integer(routeID).toString() + "/Vehicles");
            String pathJSON = htmlParser.getJSON("http://www.ucsdbus.com/Route/"
                    + new Integer(routeID).toString() + "/Waypoints");

            try
            {
                JSONObject stopsParser = new JSONObject(stopsJSON);
                JSONObject vehiclesParser = new JSONObject(vehiclesJSON);
                JSONObject pathParser = new JSONObject(pathJSON);

                int number = 1;
                while (stopsParser.has(new Integer (number).toString()))
                {
                    JSONObject curStop = stopsParser.getJSONObject(new Integer (number++).toString());
                    this.stops.add(new UCSDBusStop(curStop.getInt("ID"),
                                                    new Coordinate(curStop.getDouble("Latitude"),
                                                                    curStop.getDouble("Longitude")),
                                                    curStop.getString("Name"),
                                                    curStop.getInt("RtpiNumber")));

                }

                number = 1;
                /*
                int busID, int routeID, int patternID,
                           int nameNum, boolean hasAPC, int doorStatus, Coordinate coordinate,
                           int speed, BusDirection heading, Date lastUpdated)
                 */
                while (vehiclesParser.has(new Integer (number).toString()))
                {
                    JSONObject curVehicle = vehiclesParser.getJSONObject(new Integer (number++).toString());

                    BusDirection heading;
                    switch (curVehicle.getString("Heading"))
                    {
                        case "NE":
                            heading = BusDirection.NE;
                            break;
                        case "NW":
                            heading = BusDirection.NW;
                            break;
                        case "SE":
                            heading = BusDirection.SE;
                            break;
                        case "SW":
                            heading = BusDirection.SW;
                            break;
                        case "W":
                            heading = BusDirection.W;
                            break;
                        case "E":
                            heading = BusDirection.E;
                            break;
                        case "S":
                            heading = BusDirection.S;
                            break;
                        default:
                            heading = BusDirection.N;
                            break;
                    }

                    // TODO
                    Date date = new Date(name);

                    this.vehicles.add(new UCSDVehicle(
                            curVehicle.getInt("ID"), curVehicle.getInt("RouteId"),
                            curVehicle.getInt("PatternId"), curVehicle.getInt("Name"),
                            curVehicle.getBoolean("HasAPC"), curVehicle.getInt("DoorStatus"),
                            new Coordinate(curVehicle.getDouble("Latitude"),
                                    curVehicle.getDouble("Longitude")),
                            curVehicle.getInt("Speed"), heading, date
                    ));

                }
            }
            catch (JSONException e)
            {
                Log.d("Android : ", "Failed to parse JSON!!!");
            }
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

    UCSDBusRoute[] routes;
    final int totalNumStops = 15;

    protected String doInBackground(String... s)
    {
        Log.d("Android : ", "Getting UCSD Bus info in background!");
        htmlParser = new JSONHtmlParser();
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

        return "Success";
    }

    protected void onProgressUpdate(Void progress)
    {}

    protected void onPostExecute(String result)
    {}
}
