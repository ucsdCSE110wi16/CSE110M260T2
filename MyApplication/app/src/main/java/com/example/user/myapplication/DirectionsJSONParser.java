package com.example.user.myapplication;

/**
 * Created by EC on 3/3/2016.
 */

import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class DirectionsJSONParser {

    /** Receives a JSONObject and returns a list of lists containing latitude and longitude */
    public List<List<HashMap<String,String>>> parse(JSONObject jObject, Global g){

        List<List<HashMap<String, String>>> routes = new ArrayList<List<HashMap<String,String>>>() ;
        JSONArray jRoutes = null;
        JSONArray jLegs = null;
        JSONArray jSteps = null;
        boolean busFound=false;
        boolean isMTSorNCTD=false;
        int timetoStop=0;

        try {

            jRoutes = jObject.getJSONArray("routes");

            /** Traversing all routes */
            for(int i=0;i<jRoutes.length();i++){
                jLegs = ( (JSONObject)jRoutes.get(i)).getJSONArray("legs");
                List path = new ArrayList<HashMap<String, String>>();

                /** Traversing all legs */
                for(int j=0;j<jLegs.length();j++){
                    jSteps = ( (JSONObject)jLegs.get(j)).getJSONArray("steps");

                    /** Traversing all steps */
                    for(int k=0;k<jSteps.length();k++) {
                        String polyline = "";
                        polyline = (String) ((JSONObject) ((JSONObject) jSteps.get(k)).get("polyline")).get("points");


                        if( ((JSONObject)jSteps.get(k)).has("transit_details")){
                            //check if "MTS" or "North County Transit District"


                            String x, time,time2,name,name2;
                            double lat=0,lon=0,lat2=0,lon2=0;
                            Integer timenum=0;

                            //keep track if there is a bus in the route


                            //gets route name
                            x  =(String)(((JSONObject)jSteps.get(k)).getJSONObject("transit_details").getJSONObject("line").get("short_name"));
                            //lat lon of bus stop to get on
                            lat=(Double)(((JSONObject)jSteps.get(k)).getJSONObject("transit_details").getJSONObject("departure_stop").getJSONObject("location").get("lat"));
                            lon=(Double)(((JSONObject)jSteps.get(k)).getJSONObject("transit_details").getJSONObject("departure_stop").getJSONObject("location").get("lng"));
                            //lat lon of bus stop to get off
                            lat2=(Double)(((JSONObject)jSteps.get(k)).getJSONObject("transit_details").getJSONObject("arrival_stop").getJSONObject("location").get("lat"));
                            lon2=(Double)(((JSONObject)jSteps.get(k)).getJSONObject("transit_details").getJSONObject("arrival_stop").getJSONObject("location").get("lng"));
                            //time as string ex=10:59am
                            time=(String)(((JSONObject)jSteps.get(k)).getJSONObject("transit_details").getJSONObject("departure_time").get("text"));
                            time2=(String)(((JSONObject)jSteps.get(k)).getJSONObject("transit_details").getJSONObject("arrival_time").get("text"));
                            //name of the stop
                            name=(String)(((JSONObject)jSteps.get(k)).getJSONObject("transit_details").getJSONObject("departure_stop").get("name"));
                            name2=(String)(((JSONObject)jSteps.get(k)).getJSONObject("transit_details").getJSONObject("arrival_stop").get("name"));
                            //time as seconds since Jan 1, 1970
                            //timenum=(Integer)(((JSONObject)jSteps.get(k)).getJSONObject("transit_details").getJSONObject("departure_time").get("value"));
                            if(lat!=0 && lon!=0 && !busFound) {

                                String company=(String)((JSONObject)((JSONObject)jSteps.get(k)).getJSONObject("transit_details").getJSONObject("line").getJSONArray("agencies").get(0)).get("name");
                                Log.d("BUS COMPANY",company);
                                if (company.equals("MTS") || company.equals("North County Transit District")){
                                    isMTSorNCTD=true;
                                }
                                //if found bus stop lat long, create marker for it
                                g.setStartMarker(new MarkerOptions()
                                    .position(new LatLng(lat, lon))
                                    .title(name)
                                    .snippet(x + " arrivedfgfs: " + time)
                                );



                                SimpleDateFormat format = new SimpleDateFormat("hh:mmaa", Locale.US);
                                try {
                                    Date date = format.parse(time);
                                    g.setArriveTime(date);
                                } catch (ParseException e) {
                                    // TODO Auto-generated catch block
                                }

                                g.setBusNum(x);
                                g.setStopName(name);
                                //sets arrival time of bus to destination
                                g.setEndMarker(new MarkerOptions()
                                    .position(new LatLng(lat2,lon2))
                                    .title(name2)
                                    .snippet(x+" will reach destination at "+time2)
                                );
                                Log.d("JSON", "= departure stop lat lon: " + Double.toString(lat) + "," + Double.toString(lon));
                                Log.d("JSON","= time and seconds since 1970: " + time + "=" + Integer.toString(timenum));
                            }
                            busFound=true;
                            Log.d("JSON", "bus route: "+x);
                            Log.d("JSON","walk time to stop: "+Integer.toString(timetoStop));
                        }else{
                            if (!busFound) {
                                g.setStartMarker(null);
                            }
                        }
                        if (!busFound){
                            //if no bus found, continue adding time (still walking to bus stop)
                            timetoStop += ((Integer) ((JSONObject) jSteps.get(k)).getJSONObject("duration").get("value"));
                            //sets the time in minutes
                            g.setWalking_to_bus(timetoStop/60+1);
                        }
                        List<LatLng> list = decodePoly(polyline);

                        /** Traversing all points */
                        for(int l=0;l<list.size();l++){
                            HashMap<String, String> hm = new HashMap<String, String>();
                            hm.put("lat", Double.toString(((LatLng)list.get(l)).latitude) );
                            hm.put("lng", Double.toString(((LatLng)list.get(l)).longitude) );
                            path.add(hm);
                        }
                    }
                    routes.add(path);
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }
        //if bus stop found is not mts/nctd(101) return empty list to be found and error caught
        if(isMTSorNCTD) {
            return routes;
        }else{
            return new ArrayList<>();
        }
    }
    /**
     * Method to decode polyline points
     * Courtesy : http://jeffreysambells.com/2010/05/27/decoding-polylines-from-google-maps-direction-api-with-java
     * */
    private List<LatLng> decodePoly(String encoded) {

        List<LatLng> poly = new ArrayList<LatLng>();
        int index = 0, len = encoded.length();
        int lat = 0, lng = 0;

        while (index < len) {
            int b, shift = 0, result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;

            shift = 0;
            result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;

            LatLng p = new LatLng((((double) lat / 1E5)),
                    (((double) lng / 1E5)));
            poly.add(p);
        }

        return poly;
    }
}
