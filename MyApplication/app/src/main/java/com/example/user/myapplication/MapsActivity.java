package com.example.user.myapplication;

import android.graphics.Color;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;

import com.google.android.gms.common.api.GoogleApiClient;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONObject;

public class MapsActivity extends FragmentActivity  {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.

    String value,destination;
    List<Address> list;
    List<MtsStop> stops=new ArrayList<MtsStop>();
    String result;
    LatLng destLatLng=null, startLatLng=null;
    MarkerOptions destOpt=null,startOpt=null;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_maps);


        //setUpMTSStops();
        //MtsStop usage?
        //test();
        Global g = (Global) getApplication();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
        setUpMapIfNeeded();


    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */


    // ***************************************************************************
    // This is the code that reads creates MTSbusstop stuff
    // Code by: Eric
    // File for static MTS bus stops and times listed in stop_filter under documents folder.
    // ****************************************************************************
    /*
    //*******************************************************************************/
    private void test(){
        Log.d("MTS STOPS", Integer.toString(stops.size()));
        //Search route or stop from stopID/routeID by making new stop/route with id
        Log.d("MTS STOPS","finds stop from a stop(MtsStop): "+Boolean.toString(stops.contains(new MtsStop("11151"))));
        if(stops.contains(new MtsStop("11151"))){
            MtsStop temp= (MtsStop)stops.get(stops.indexOf(new MtsStop("11151")));
            if(temp==null){
                Log.d("MTS_STOPS","stop is null");
            }else {
                Log.d("MTS_STOPS", temp.getID());
                for (Route r : temp.routes) {
                    Log.d("MTS_STOPS", "routeid " + r.getID());

                }
            }
            Log.d("MTS STOPS",Boolean.toString(temp.contains(new Route("202"))));
        }
    }
    private void setUpMTSStops() {
        String line=null;
        String lat=null, lon=null, code=null,name=null;
        String routeID=null,time=null;
        double latNum,lonNum;
        int index=0, last=0;


        try {
            InputStream ins = getResources().openRawResource(
                    getResources().getIdentifier("output",
                            "raw", getPackageName()));

            InputStreamReader inputReader = new InputStreamReader(ins);
            BufferedReader bRead = new BufferedReader(inputReader);
            int count=0;
            MtsStop s=null;
            Route r;
            while (( line = bRead.readLine()) != null) {


                if(line.contains("+")) {
                    last = line.indexOf(",");
                    //finds lattitude
                    lat = line.substring(0, last);

                    index = last;
                    last = line.indexOf(",", index + 1);
                    //finds longitude
                    lon = line.substring(index + 1, last);

                    index = last;
                    last = line.indexOf(",", index + 1);
                    //finds stopID
                    code = line.substring(index + 1, last);

                    index = last;
                    //finds name of stop
                    name = line.substring(index + 1, line.length());

                    latNum = Double.parseDouble(lat);
                    lonNum = Double.parseDouble(lon);
                    //creates new stop

                    s=new MtsStop(latNum,lonNum,code,name);
                    stops.add(s);
                }else if (line.contains("_")){
                    last=line.indexOf("_");
                    routeID=line.substring(0, last);

                    r=new Route(routeID);
                    //if (routeID.equals("202")) {
                        while (last < line.length() - 1) {
                            index = last;
                            last = line.indexOf(",", index + 1);
                            time = line.substring(index + 1, last);
                            //adds into priority queue to be sorted
                            r.addTime(time);
                            count++;

                        }
                        //when done adding times, adds to final list
                        //Log.d("MTS", Integer.toString(count));
                        r.finalizeList();
                        s.addRoute(r);
                    //}


                }
            }
            //Log.d("MTS",Integer.toString(count));
        } catch (IOException e) {
            Log.e("ERROR", "ERROR READING MTS STOPS");
        }

    }

    //******************************************************************
    private void setUpMap(){

        Global g = (Global)getApplication();

        Bundle extras = getIntent().getExtras();

        if(g.getData_method().equals("address")) {
            //if user entered an address/current location and destination
            if (extras != null) {
                value = extras.getString("address_entered");
                destination = extras.getString("destination");
            }

            if (value == null || destination == null) {
                Toast.makeText(this, value, Toast.LENGTH_LONG).show();
            } else {
                //uses current location if "Your Location" was entered
                destOpt = getMarkerOpt(destination);
                if (value.equals("Your Location")) {
                    Geocoder geocoder = new Geocoder(this);
                    List l = null;
                    LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

                    // Create a criteria object to retrieve provider
                    Criteria criteria = new Criteria();
                    // Get the name of the best provider
                    String provider = locationManager.getBestProvider(criteria, true);
                    // Get Current Location
                    Location myLocation = null;
                    try {
                        myLocation = locationManager.getLastKnownLocation(provider);
                        mMap.setMyLocationEnabled(true);
                    } catch (SecurityException e) {
                        e.printStackTrace();
                        Toast.makeText(this, "Location services not enabled", Toast.LENGTH_LONG).show();
                    }

                    if (myLocation == null) {
                        Log.d("LOCATION", "location was null");
                        Toast.makeText(this, "Current Location not found.", Toast.LENGTH_SHORT).show();
                        this.finish();
                    } else {
                        try {
                            l = geocoder.getFromLocation(myLocation.getLatitude(),
                                    myLocation.getLongitude(), 1);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        if (l == null || l.isEmpty()) {
                            Toast.makeText(this, "Current Location not found.", Toast.LENGTH_SHORT).show();
                        } else {
                            Address a = (Address) l.get(0);
                            double lat = a.getLatitude();
                            double lon = a.getLongitude();
                            String line = a.getAddressLine(0);
                            startLatLng = new LatLng(lat, lon);
                            startOpt = (new MarkerOptions()
                                    .position(startLatLng)
                                    .draggable(true)
                                    .title(line));
                            Log.d("ADDRESS", a.getAddressLine(0));
                            Log.d("ADDRESS", a.getAddressLine(1));
                            Log.d("ADDRESS", a.getAddressLine(2));
                        }
                    }
                } else {
                    //handles an address
                    startOpt = getMarkerOpt(value);
                }


                if (startOpt != null && destOpt != null) {
                    String url = getDirectionsUrl(startOpt.getPosition(), destOpt.getPosition());

                    DownloadTask downloadTask = new DownloadTask();

                    // Start downloading json data from Google Directions API
                    downloadTask.execute(url);
                }
            }
        }else if(g.getData_method().equals("id")){
            //use stop or route number to get bus stop
        }


    }

    private MarkerOptions getMarkerOpt(String s){
        Geocoder gc=new Geocoder(this);
        LatLng point=null;
        try {

            list = gc.getFromLocationName(s, 1);

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        if(list.isEmpty()){
            Toast.makeText(this, "Location not found.", Toast.LENGTH_SHORT).show();
            this.finish();
        }
        else{
            Address add = list.get(0);
            double latitude = add.getLatitude();
            double longitude = add.getLongitude();

            String address_line = add.getAddressLine(0);

            point = new LatLng(latitude, longitude);
            return (new MarkerOptions()
                    .position(point)
                    .draggable(true)
                    .title(address_line));


        }
        return null;
    }

    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }



    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Maps Page", // TODO: Define a title for the content shown.
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
                "Maps Page", // TODO: Define a title for the content shown.
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



    //Directions 2 points methods below
    private String getDirectionsUrl(LatLng origin,LatLng dest){

        // Origin of route
        String str_origin = "origin="+origin.latitude+","+origin.longitude;

        // Destination of route
        String str_dest = "destination="+dest.latitude+","+dest.longitude;

        // Sensor enabled
        String sensor = "sensor=false";
        //String time ="departure_time=1457124650";


        String mode = "mode=transit";

        // Building the parameters to the web service
        String parameters = str_origin+"&"+str_dest+"&"+mode+"&"+sensor;
        //if ever use time parameter
        //String parameters = str_origin+"&"+str_dest+"&"+mode+"&"+time+"&"+sensor;

        // Output format
        String output = "json";

        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/"+output+"?"+parameters;
        Log.d("GOOGLE URL",url);
        return url;
        //url for testing ucsd to nobel drive, march 4, 12:51pm
        //return "https://maps.googleapis.com/maps/api/directions/json?origin=32.8816136,-117.2385671&destination=32.867186,-117.2121012&mode=transit&departure_time=1457124650&sensor=false";
    }
    /** A method to download json data from url */
    private String downloadUrl(String strUrl) throws IOException{
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try{
            URL url = new URL(strUrl);

            // Creating an http connection to communicate with url
            urlConnection = (HttpURLConnection) url.openConnection();

            // Connecting to url
            urlConnection.connect();

            // Reading data from url
            iStream = urlConnection.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

            StringBuffer sb = new StringBuffer();

            String line = "";
            while( ( line = br.readLine()) != null){
                sb.append(line);
            }

            data = sb.toString();

            br.close();

        }catch(Exception e){
            Log.d("ERROR downloading url", e.toString());
        }finally{
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }

    // Fetches data from url passed
    private class DownloadTask extends AsyncTask<String, Void, String>{

        // Downloading data in non-ui thread
        @Override
        protected String doInBackground(String... url) {

            // For storing data from web service
            String data = "";

            try{
                // Fetching the data from web service
                data = downloadUrl(url[0]);
            }catch(Exception e){
                Log.d("Background Task",e.toString());
            }
            return data;
        }

        // Executes in UI thread, after the execution of
        // doInBackground()
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            ParserTask parserTask = new ParserTask();

            // Invokes the thread for parsing the JSON data
            parserTask.execute(result);
        }
    }

    /** A class to parse the Google Places in JSON format */
    private class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String,String>>> > {

        // Parsing the data in non-ui thread
        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {

            JSONObject jObject;
            List<List<HashMap<String, String>>> routes = null;

            try{
                jObject = new JSONObject(jsonData[0]);
                DirectionsJSONParser parser = new DirectionsJSONParser();

                // Starts parsing data
                routes = parser.parse(jObject, (Global) getApplication());
            }catch(Exception e){
                e.printStackTrace();
            }
            return routes;
        }

        // Executes in UI thread, after the parsing process
        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> result) {
            ArrayList<LatLng> points = null;
            PolylineOptions lineOptions = null;
            MarkerOptions markerOptions = new MarkerOptions();

            // Traversing through all the routes
            for(int i=0;i<result.size();i++){
                points = new ArrayList<LatLng>();
                lineOptions = new PolylineOptions();

                // Fetching i-th route
                List<HashMap<String, String>> path = result.get(i);

                // Fetching all the points in i-th route
                for(int j=0;j<path.size();j++){
                    HashMap<String,String> point = path.get(j);

                    double lat = Double.parseDouble(point.get("lat"));
                    double lng = Double.parseDouble(point.get("lng"));
                    LatLng position = new LatLng(lat, lng);

                    points.add(position);
                }

                // Adding all the points in the route to LineOptions
                lineOptions.addAll(points);
                lineOptions.width(4);
                lineOptions.color(Color.RED);
            }

            // Drawing polyline in the Google Map for the i-th route
            mMap.addPolyline(lineOptions);

            Global g = (Global) getApplication();
            //if there is a bus available
            if(g.getStartMarker()!=null) {
                //add bus stop to map
                mMap.addMarker(g.getStartMarker());
                mMap.addMarker(g.getEndMarker());
                if(startOpt!=null && destOpt!=null){
                    //add text to starting/end markers for how long to get to stop and destination
                    startOpt.snippet(Integer.toString(g.getWalking_to_bus()) + "m to walk to bus stop");
                }
            }else{
                //if no buses available, tell user
                Toast.makeText(getApplicationContext(), "No Buses Available", Toast.LENGTH_LONG).show();
            }

            //adds the markers to the map
            mMap.addMarker(startOpt);
            mMap.addMarker(destOpt);
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(startOpt.getPosition(),15));
            //mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
        }
    }
}
