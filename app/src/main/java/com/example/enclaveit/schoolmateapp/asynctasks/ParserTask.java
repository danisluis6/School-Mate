package com.example.enclaveit.schoolmateapp.asynctasks;

import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;

import com.example.enclaveit.schoolmateapp.libraries.DataParser;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by enclaveit on 22/03/2017.
 */

public class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String, String>>>> {
    GoogleMap gMap;
    public ParserTask(GoogleMap googleMap){
        this.gMap = googleMap;
    }

    /*Passing the data in non-UI Thread*/
@Override
protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {
        JSONObject jObject;
        List<List<HashMap<String, String>>> routes = null;
        try{
        jObject = new JSONObject(jsonData[0]);
        //Log.d("PARSERTASK", "Parser Task data: "+jsonData[0].toString());
        DataParser parser = new DataParser();
        //Log.d("PARSERTASK", parser.toString());

                /*Start parsing data*/
        routes = parser.parse(jObject);
        //Log.d("PARSERTASK", "Executing routes");
        //Log.d("PARSERTASK", routes.toString());

        }catch (Exception e){
            Log.d("PARSERTASK", "Error when parse data from json: "+e.toString());
            e.printStackTrace();
        }
        return routes;
        }

        /*Executes in UI Thread after parsing process*/

@Override
protected void onPostExecute(List<List<HashMap<String, String>>> result) {
        super.onPostExecute(result);
        ArrayList<LatLng> points;
        PolylineOptions lineOptions = null;

        //Traversing through all the routes
        for (int i=0; i<result.size(); i++){
            points = new ArrayList<>();
            lineOptions = new PolylineOptions();
                    /*Fetching i-th route*/
            List<HashMap<String, String>> path = result.get(i);
                /*Fetching all the points in i-th*/
                for (int j=0; j< path.size(); j++){
                    HashMap<String, String> point = path.get(j);
                    double lat = Double.parseDouble(point.get("lat"));
                    double lng = Double.parseDouble(point.get("lng"));
                    LatLng position = new LatLng(lat, lng);

                    points.add(position);
                }

                    /*Adding all the points to the route to LineOptions*/
            lineOptions.addAll(points);
            lineOptions.width(10);
            lineOptions.color(Color.RED);
            Log.i("OnPostExecuteParserTask", "Decoded");
        }

            /*Drawing polyline in the Google Map for the i-th route*/
            if(lineOptions != null ){
                    gMap.addPolyline(lineOptions);
            }else Log.i("PARSER TASK", "Without Polylines drawn");
    }
}
