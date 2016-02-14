package com.example.han.googleinterface;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    Location mLastLocation;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera..
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in UCSD and move the camera
        LatLng UCSD = new LatLng(32.875362, -117.235862);
        mMap.addMarker(new MarkerOptions().position(UCSD).title("Marker in UCSD").
                icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));

        // Set map type to be hybrid
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

        // Find user current location
        findMyLoc();
    }

    /**
     * Function name: findMyLoc
     * Description: Keep track of user current location;Then,move the camera to that location
     *              and put a maker there.
     */
    private void findMyLoc(){
        // Enable MyLocation Layer of Google Map
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        }

        // Get LocationManager object from System Service LOCATION_SERVICE
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        // Create a criteria object to retrieve provider
        Criteria criteria = new Criteria();

        // Get the name of the best provider
        String provider = locationManager.getBestProvider(criteria, true);

        // Get Current Location
        mLastLocation = locationManager.getLastKnownLocation(provider);

        // Get latitude and longitude of user current location
        double latitude = mLastLocation.getLatitude();
        double longitude = mLastLocation.getLongitude();

        // Create a LatLng object for the current location
        LatLng currLoc = new LatLng(latitude, longitude);

        // Show the current location in Google Map
        mMap.moveCamera(CameraUpdateFactory.newLatLng(currLoc));

        // Zoom in the Google Map
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
        mMap.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude))
                .title("You are here!"));
    }

    /**
     * Function name: onSearch(View view)
     * Description: Search for the user input address in the google maps
     */
    public void onSearch(View view){

        // Get user input location text, and convert to string
        EditText location_tf = (EditText)findViewById(R.id.TFaddress);
        String location = location_tf.getText().toString();

        // Store the list of address
        List<Address> addressList = null;

        // Check whether user input location is valid
        if(location != null || !location.equals("")) {

            Geocoder geocoder = new Geocoder(this);
            try {
                addressList = geocoder.getFromLocationName(location, 1);

            } catch (IOException e) {
                e.printStackTrace();
            }

            // Store the latitude and longitude of user input address
            Address address = addressList.get(0);
            LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());

            // Add a maker on the user input address
            mMap.addMarker(new MarkerOptions().position(latLng).title("I want to go there!"));

            // Move camera to that found address
            mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
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
                Uri.parse("android-app://com.example.han.googleinterface/http/host/path")
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
                Uri.parse("android-app://com.example.han.googleinterface/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}
