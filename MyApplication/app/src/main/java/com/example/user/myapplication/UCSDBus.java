package com.example.user.myapplication;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

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
        public double getDist (Coordinate other)
        {
            double hypotenuse = 0.0;
            double diffX = other.latitude - this.latitude;
            double diffY = other.longitude - this.longitude;
            hypotenuse = diffX * diffX + diffY * diffY;
            hypotenuse = Math.sqrt(hypotenuse);
            return hypotenuse;
        }
    }

    enum BusDirection
    {
        N, NE, E, SE, S, SW, W, NW
    }

    class UCSDArrival
    {
        private int routeID;
        private double secondsToArrival;

        public double getSecondsToArrival()
        {
            return secondsToArrival;
        }

        public int getRouteID()
        {
            return routeID;
        }
        public UCSDArrival (int routeID, double secondsToArrival)
        {
            this.routeID = routeID;
            this.secondsToArrival = secondsToArrival;
        }
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

        private int busID;
        private int routeID;
        private int patternID;
        private int nameNum;
        private boolean hasAPC;
        private int doorStatus;
        private Coordinate coordinate;
        private int speed;
        private BusDirection heading;

        public UCSDVehicle(int busID, int routeID, int patternID, int nameNum, boolean hasAPC,
                           int doorStatus, Coordinate coordinate,
                           int speed, BusDirection heading)
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
        }
    }

    class UCSDBusStop
    {
        private int stopID;
        private Coordinate coordinate;

        public ArrayList<UCSDArrival> arrivals;

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
            this.coordinate = new Coordinate(coordinate.latitude,
                    coordinate.longitude);
            this.stopName = new String(stopName);
            this.rtpiNumber = rtpiNumber;

            arrivals = new ArrayList<UCSDArrival>();
            String arrivalsJSON = htmlParser.getJSON("http://www.ucsdbus.com/Stop/"
                    +  Integer.toString(stopID) + "/Arrivals");

            try
            {
                JSONObject arrivalsParser = new JSONObject(arrivalsJSON);

                int number = 1;
                while (arrivalsParser.has( Integer.toString(number)))
                {
                    int numberAlt = 1;
                    JSONObject curArrival = arrivalsParser.getJSONObject(Integer.toString (number++));
                    curArrival = curArrival.getJSONObject("Arrivals");
                    while (curArrival.has(Integer.toString(numberAlt)))
                    {
                        JSONObject curArrivalAlt = curArrival.getJSONObject(Integer.toString(numberAlt++));
                        this.arrivals.add(new UCSDArrival(curArrivalAlt.getInt("RouteID"),
                                curArrivalAlt.getDouble("SecondsToArrival")));
                    }
                }
            }
            catch (JSONException e)
            {
                Log.d("Android : ", "Failed to parse vehicle JSON!!!");
            }
        }
    }//end ucsd stop

    class UCSDBusRoute
    {
        private int routeID;
        private String name;
        public ArrayList<UCSDBusStop> stops;

        public void update()
        {
            stops = new ArrayList<UCSDBusStop>();
            String stopsJSON = htmlParser.getJSON("http://www.ucsdbus.com/Route/"
                    + new Integer(routeID).toString() + "/Direction/0/Stops");
            /*
            String vehiclesJSON = htmlParser.getJSON("http://www.ucsdbus.com/Route/"
                    + new Integer(routeID).toString() + "/Vehicles");
            String pathJSON = htmlParser.getJSON("http://www.ucsdbus.com/Route/"
                    + new Integer(routeID).toString() + "/Waypoints");
            */
            try
            {
                JSONObject stopsParser = new JSONObject(stopsJSON);

                /*
                JSONObject vehiclesParser = new JSONObject(vehiclesJSON);
                JSONObject pathParser = new JSONObject(pathJSON);
                */

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

                /*

                number = 1;
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

                    this.vehicles.add(new UCSDVehicle(
                            curVehicle.getInt("ID"), curVehicle.getInt("RouteId"),
                            curVehicle.getInt("PatternId"), curVehicle.getInt("Name"),
                            curVehicle.getBoolean("HasAPC"), curVehicle.getInt("DoorStatus"),
                            new Coordinate(curVehicle.getDouble("Latitude"),
                                    curVehicle.getDouble("Longitude")),
                            curVehicle.getInt("Speed"), heading
                    ));

                }

                number = 1;
                while (pathParser.has(new Integer (number).toString()))
                {
                    JSONObject curPath = pathParser.getJSONObject(new Integer (number++).toString());
                    this.path.add(new Coordinate(curPath.getDouble("Latitude"),
                            curPath.getDouble("Longitude")));

                }

                */
            }
            catch (JSONException e)
            {
                Log.d("Android : ", "Failed to parse JSON!!!");
            }
        }

        // constructor
        public UCSDBusRoute(int routeID, String name)
        {
            this.routeID = routeID;
            this.name = name;
            update();
        }

        public int getRouteID()
        {
            return routeID;
        }

        public String getName()
        {
            return name;
        }
    }//end ucsdbusroute

    ArrayList<UCSDBusRoute> routes;
    final int totalNumStops = 15;

    @Override
    protected String doInBackground(String... s)
    {
        Log.d("Android : ", "Getting UCSD Bus info in background!");
        htmlParser = new JSONHtmlParser();
        routes = new ArrayList<UCSDBusRoute>();

        this.routes.add(new UCSDBusRoute(3168, "(A)(N) City Shuttle (Arriba/Nobel)"));
        this.routes.add(new UCSDBusRoute(312, "(C) Coaster East"));
        this.routes.add(new UCSDBusRoute(2092, "(C)(W) Coaster East/West (mid-day)"));
        this.routes.add(new UCSDBusRoute(314, "(CP) Chancellor's Park"));
        this.routes.add(new UCSDBusRoute(1114, "(H) Hillcrest/Campus A.M."));
        this.routes.add(new UCSDBusRoute(1264, "(H) Hillcrest/Campus P.M."));
        this.routes.add(new UCSDBusRoute(3442, "(L) Clockwise Campus Loop - Peterson Hall to Torrey Pines Center"));
        this.routes.add(new UCSDBusRoute(1263, "(L) Counter Campus Loop - Torrey Pines Center to Peterson Hall"));
        this.routes.add(new UCSDBusRoute(1113, "(L) Evening / Weekend Clockwise Campus Loop"));
        this.routes.add(new UCSDBusRoute(3159, "(L) Evening / Weekend Counter Clockwise Campus Loop"));
        this.routes.add(new UCSDBusRoute(3440, "(M) Mesa"));
        this.routes.add(new UCSDBusRoute(1098, "(P) Regents"));
        this.routes.add(new UCSDBusRoute(2399, "(S) SIO Loop"));
        this.routes.add(new UCSDBusRoute(1434, "(SC) Sanford Consortium Shuttle"));
        this.routes.add(new UCSDBusRoute(313, "(W) Coaster West"));

        return "Success";
    }

    protected void onProgressUpdate(Void progress)
    {}

    protected void onPostExecute(String result)
    {

        Log.d("TEST COORD",getNearestStop(new Coordinate(0,0)).getStopName());
    }

    public double getSecondsToArrival(UCSDBusStop stop, UCSDBusRoute route)
    {
        for (UCSDArrival a : stop.arrivals)
        {
            if (a.getRouteID() == route.getRouteID())
            {
                return a.getSecondsToArrival();
            }
        }
        return -1.0;
    }

    public UCSDBusStop getNearestStop(Coordinate coord)
    {
        UCSDBusStop nearest = null;

        for (int i = 0; i < routes.size(); i++)
        {
            Log.d("UCSD BUs","HERE");
            for (int j = 0; j < routes.get(i).stops.size(); j++)
            {
                UCSDBusStop curStop = routes.get(i).stops.get(j);
                if (nearest == null)
                {
                    nearest = curStop;
                }
                else if (coord.getDist(curStop.getCoordinate()) < coord.getDist(nearest.getCoordinate()))
                {
                    nearest = curStop;
                }
            }
        }

        return nearest;
    }
}
