package com.example.user.myapplication;

import android.content.IntentSender;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import com.example.user.myapplication.MtsStop;

public class MapsActivity extends FragmentActivity  {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.

    String value;
    List<Address> list;
    List<MtsStop> stops=new ArrayList<MtsStop>();
    String result;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_maps);
        setUpMapIfNeeded();
        setUpMTSStops();
        Log.d("MTS STOPS",Integer.toString(stops.size()));

        Global g = (Global) getApplication();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
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
    private void setUpMTSStops() {
        String line=null;
        String lat=null, lon=null, code=null,name=null;
        String routeID=null,time=null;
        double latNum,lonNum;
        int index=0, last=0;
        InputStream ins = getResources().openRawResource(
                getResources().getIdentifier("output",
                        "raw", getPackageName()));

        InputStreamReader inputReader = new InputStreamReader(ins);
        BufferedReader bRead = new BufferedReader(inputReader);
        try {

            while (( line = bRead.readLine()) != null) {
                MtsStop s=null;
                Route r=null;
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
                    //finds stopid
                    code = line.substring(index + 1, last);

                    index = last;
                    //finds name of stop
                    name = line.substring(index + 1, line.length());

                    latNum = Double.parseDouble(lat);
                    lonNum = Double.parseDouble(lon);
                    s=new MtsStop(latNum,lonNum,code,name);
                    stops.add(s);
                }else if (line.contains("_")){
                    last=line.indexOf("_");
                    routeID=line.substring(0, last);
                    r=new Route(routeID);
                    while(last<line.length()-1) {
                        index=last;
                        last = line.indexOf(",", index + 1);
                        time=line.substring(index + 1, last);
                        r.addTime(time);
                    }
                    if(s!=null) {
                        s.addRoute(r);
                    }


                }



            }
        } catch (IOException e) {
            Log.e("ERROR", "ERROR READING MTS STOPS");
        }

    }

    //******************************************************************
    private void setUpMap(){

        Global g = (Global)getApplication();

        if(g.getData_method()=="auto") {
            mMap.setMyLocationEnabled(true);
            LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

            // Create a criteria object to retrieve provider
            Criteria criteria = new Criteria();
            // Get the name of the best provider
            String provider = locationManager.getBestProvider(criteria, true);
            // Get Current Location
            Location myLocation = locationManager.getLastKnownLocation(provider);
            //set map type
            mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            // Get latitude of the current location
            double latitude = myLocation.getLatitude();
            // Get longitude of the current location
            double longitude = myLocation.getLongitude();
            // Create a LatLng object for the current location
            LatLng latLng = new LatLng(latitude, longitude);
            // Show the current location in Google Map
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            // Zoom in the Google Map
            mMap.animateCamera(CameraUpdateFactory.zoomTo(15));

        }
        else if(g.getData_method()=="address") {
            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                value = extras.getString("address_entered");
            }
            Geocoder gc = new Geocoder(this);

            if(value == null){
                Toast.makeText(this, value, Toast.LENGTH_LONG).show();
            }
            else {
                try {
                    list = gc.getFromLocationName(value, 1);

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

                    LatLng latLng = new LatLng(latitude, longitude);
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                    mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
                    mMap.addMarker(new MarkerOptions()
                            .position(latLng)
                            .draggable(true)
                            .title(address_line));
                }
            }
        }

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
}
