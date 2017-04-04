package com.example.enclaveit.schoolmateapp.asynctasks;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import com.example.enclaveit.schoolmateapp.activities.ActivityBusTracking;
import com.google.android.gms.maps.model.LatLng;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by enclaveit on 22/03/2017.
 */

public class GetLocationAsyncTask extends AsyncTask<String, Void, String> {

    private HttpClient client = new DefaultHttpClient();
    private HttpGet httpGet ;
    private HttpResponse response;
    private HttpEntity httpEntity;
    Context context;

    public GetLocationAsyncTask(Context context){ this.context = context;}

    @Override
    protected String doInBackground(String... params) {
        String url = params[0];
        httpGet = new HttpGet(url);
        String content ="";
        try{
            response = client.execute(httpGet);
            StatusLine statusLine = response.getStatusLine();
            int statusCode= statusLine.getStatusCode();
            if(statusCode == 200){
                httpEntity = response.getEntity();
                InputStream is = httpEntity.getContent();
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                StringBuilder stringBuilder = new StringBuilder();
                String line = "";
                while((line = br.readLine()) != null){
                    stringBuilder.append(line);
                }
                content = stringBuilder.toString();
                return content;
            }else{
                Log.i("Message", "Fail to get data from server");
            }
        } catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        try {
            JSONObject allLocations = new JSONObject(result);
            Log.i("Message", "Length of allLocations: " + allLocations.length());
            LatLng myLocation = null, schoolLocation, endLocation;
            JSONObject jsonObjectSchool = allLocations.getJSONObject("schoolLocation");
                schoolLocation = new LatLng(jsonObjectSchool.getDouble("lat"), jsonObjectSchool.getDouble("lng"));
            JSONObject jsonObjectEnd = allLocations.getJSONObject("endLocation");
                endLocation = new LatLng(jsonObjectEnd.getDouble("lat"), jsonObjectEnd.getDouble("lng"));
            try {
                JSONObject jsonObjectMe = allLocations.getJSONObject("myLocation");
                myLocation = new LatLng(jsonObjectMe.getDouble("lat"), jsonObjectMe.getDouble("lng"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            ActivityBusTracking busTrackingActivity = (ActivityBusTracking) context;
                busTrackingActivity.setAllLocation(schoolLocation, endLocation, myLocation);

            Intent sendResult = new Intent("com.example.enclaveit.schoolmateapp.BusTackingIntent");
                sendResult.putExtra("myLocation", (myLocation!=null)?true:false);
            context.sendBroadcast(sendResult);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
