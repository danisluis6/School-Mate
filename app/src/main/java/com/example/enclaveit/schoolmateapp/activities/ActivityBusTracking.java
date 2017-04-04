package com.example.enclaveit.schoolmateapp.activities;

import android.Manifest;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.example.enclaveit.schoolmateapp.R;
import com.example.enclaveit.schoolmateapp.asynctasks.FetchUrlAsyncTask;
import com.example.enclaveit.schoolmateapp.asynctasks.GetLocationAsyncTask;
import com.example.enclaveit.schoolmateapp.libraries.ActivityBase;
import com.example.enclaveit.schoolmateapp.libraries.GetMyLocation;
import com.example.enclaveit.schoolmateapp.libraries.PubNubClass;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.GroundOverlay;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.pubnub.api.PubNub;
import com.pubnub.api.callbacks.SubscribeCallback;
import com.pubnub.api.enums.PNStatusCategory;
import com.pubnub.api.models.consumer.PNStatus;
import com.pubnub.api.models.consumer.pubsub.PNMessageResult;
import com.pubnub.api.models.consumer.pubsub.PNPresenceEventResult;

import org.json.JSONException;
import org.json.JSONObject;
import java.util.Arrays;

/**
 * Created by enclaveit on 01/03/2017.
 */

public class ActivityBusTracking extends ActivityBase implements OnMapReadyCallback {
    private static final String MESSAGE = "BUS TRACKING";
    private static final int ACCESS_COARSE_LOCATION = 1;
    private static final int ACCESS_FINE_LOCATION = 2;

    GoogleMap gMap;
    LatLng myLocation, busLocation, schoolLocation, endLocation;
    /*Used to draw and determine the position of bus, house and school*/
    GroundOverlayOptions houseGroundOverlay, schoolGroundOverlay, busGroundOverlay, endGroundOverlay;
    GroundOverlay busOverlay;   //to change bus' position

    PubNub pubNub;
    BroadcastReceiver broadcastReceiverBusTracking;

    @Override
    protected void onCreate(Bundle savedInstanceStatus) {
        super.onCreate(savedInstanceStatus);
        setContentView(R.layout.bus_tracking_activity_layout);
        Log.i(MESSAGE, "On BusTrackingActivity Create");
        //set Toolbar Title
        setToolbarTitle(getResources().getString(R.string.bus_tracking));

        pubNub = new PubNubClass(this).getPubNub();
        /*Init gMap*/
        if(gMap == null){
            MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.mapFragment);
            mapFragment.getMapAsync(this);
            Log.i(MESSAGE, "Recreate Map");
        /*Subscribe to the DemoRealTimeChannel channel*/
            pubNub.addListener(new SubscribeCallback() {
                @Override
                public void status(PubNub pubnub, PNStatus status) {
                    Log.i("Message", "Status : "+status.getCategory().toString());
                    if (status.getCategory() == PNStatusCategory.PNUnexpectedDisconnectCategory) {
                        Log.i("Message", "Disconnected !!");
                    }else if (status.getCategory() == PNStatusCategory.PNConnectedCategory) {
                        Log.i("Message", "Connected !!");
                    }
                    else if (status.getCategory() == PNStatusCategory.PNReconnectedCategory) {
                        Log.i(MESSAGE, "Reconnected !!");
                    }
                    else if (status.getCategory() == PNStatusCategory.PNDecryptionErrorCategory) {
                        Log.i(MESSAGE, "decryption !!");
                    }
                }

                @Override
                public void message(PubNub pubnub, PNMessageResult message) {
                    if(message.getChannel() != null ){
                        try {
                            JSONObject result = new JSONObject(message.getMessage().toString());
                            busLocation = new LatLng(result.getDouble("lat"), result.getDouble("lng"));
                            /*Run on UI*/
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    updateBusLocation();
                                }
                            });
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    else {
                        Log.i(MESSAGE, message.getSubscription());
                    }
                }
                @Override
                public void presence(PubNub pubnub, PNPresenceEventResult presence) {}
            });

            pubNub.subscribe().channels(Arrays.asList("DemoRealTimeChannel")).execute();
        }

    }

    /*Call after getMapAsync()*/
    @Override
    public void onMapReady(GoogleMap googleMap) {
        gMap = googleMap;

        //Get my location on Server
        GetLocationAsyncTask getLocationAsyncTask = new GetLocationAsyncTask(this);
            /*Get schooLocation, endLocation and myLocation in onPostExecute*/
        //TODO- pass parent ID to this function
        getLocationAsyncTask.execute("http://10.0.3.2:8886/location?objectID="+2);
        IntentFilter intentFilter = new IntentFilter("com.example.enclaveit.schoolmateapp.BusTackingIntent");
        if (broadcastReceiverBusTracking != null) this.unregisterReceiver(broadcastReceiverBusTracking);
        broadcastReceiverBusTracking = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                //If on server doesn't has myLocation
                Log.i(MESSAGE, "MyLocation is exist?? "+intent.getBooleanExtra("myLocation", false));
                if (intent.getBooleanExtra("myLocation", false) == false) {
                    /*Check Permission*/
                    if (ActivityCompat.checkSelfPermission(ActivityBusTracking.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                            && ActivityCompat.checkSelfPermission(ActivityBusTracking.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        /*Require Location Permission*/
                        ActivityCompat.requestPermissions(ActivityBusTracking.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, ACCESS_COARSE_LOCATION);
                        ActivityCompat.requestPermissions(ActivityBusTracking.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, ACCESS_FINE_LOCATION);
                    }
                    gMap.setMyLocationEnabled(true);
                    myLocation = new GetMyLocation(ActivityBusTracking.this).getMyPosition();
                }
                if (myLocation != null){
                    //Remove all old positions
                    if(gMap != null) gMap.clear();
                    gMap.addMarker(new MarkerOptions().position(myLocation).title("My house"));
                    gMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                    gMap.setBuildingsEnabled(false);
                    gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myLocation, 15));
                    /*Draw a route from school to house*/
                    drawRoute();
                    /*Set image position for House, End and School*/
                    setSchoolPosition();
                    setHousePosition();
                    setEndPosition();
                    /*Bus position automatically update when received data from server by using PubNub on subscribe*/
                }else {
                    new AlertDialog.Builder(ActivityBusTracking.this)
                            .setTitle("Confirm your information")
                            .setMessage("Your house location is not initialized on server. Contact to school to create one. \nDo you want to try using your location?")
                            .setPositiveButton("OK & Go to setting", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                    startActivity(intent);
                                }
                            })
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                }
                            }).show();
                }
            }
        };

        /*Register our receiver*/
        this.registerReceiver(broadcastReceiverBusTracking, intentFilter);
    }

    private void setSchoolPosition() {
        schoolGroundOverlay = new GroundOverlayOptions()
                .image(BitmapDescriptorFactory.fromResource(R.mipmap.school))
                .position(schoolLocation, 60f, 60f).zIndex(10);
        gMap.addGroundOverlay(schoolGroundOverlay);
    }

    private void setHousePosition(){
        houseGroundOverlay = new GroundOverlayOptions()
                .image(BitmapDescriptorFactory.fromResource(R.mipmap.house))
                .position(myLocation, 60f, 60f).zIndex(10);
        gMap.addGroundOverlay(houseGroundOverlay);
    }
    private void setEndPosition(){
        endGroundOverlay = new GroundOverlayOptions()
                .image(BitmapDescriptorFactory.fromResource(R.mipmap.stop))
                .position(endLocation, 60f, 60f).zIndex(10);
        gMap.addGroundOverlay(endGroundOverlay);
    }
    private void drawRoute(){
        //Getting URL to the Google Direction API
        String url = getUrl(schoolLocation, endLocation);
        Log.i(MESSAGE, "URL to send to Google Direction API: "+url);

        /*AsyncTask*/
        FetchUrlAsyncTask fetchUrl = new FetchUrlAsyncTask(gMap);
        /*Start downloading json data from Google Directions API*/
        fetchUrl.execute(url);
        /*//move Camera
        gMap.moveCamera(CameraUpdateFactory.newLatLng(myLocation));
        gMap.animateCamera(CameraUpdateFactory.zoomTo(12));*/
    }

    private void updateBusLocation(){
        if(busOverlay == null){
            busGroundOverlay = new GroundOverlayOptions()
                    .image(BitmapDescriptorFactory.fromResource(R.mipmap.bus))
                    .position(busLocation, 50f, 50f);
            busOverlay = gMap.addGroundOverlay(busGroundOverlay);
        }else {
            /*Change position of the Bus*/
            busOverlay.setPosition(busLocation);
            Log.i(MESSAGE, "Bus was updated");
        }
    }

    public void setAllLocation(LatLng schoolLocation, LatLng endLocation, LatLng myLocation) {
        this.schoolLocation = schoolLocation;
        this.endLocation = endLocation;
        this.myLocation = myLocation;
    }

    /*Getting URL to the Google Directions API */
    private String getUrl(LatLng origin, LatLng dest) {
        /*Origin of route*/
        String strOrigin = "origin=" + origin.latitude + "," + origin.longitude;
        /*Destination of route*/
        String strDestination = "destination=" + dest.latitude+ "," + dest.longitude;
        //Disable Sensor
        String sensor = "sensor=false";
        /*Building the parameters ti the web service*/
        String parameters = strOrigin + "&" + strDestination + "&" + sensor;

        //Output format
        String outputFormat = "json";
        /*Building the url to the web service*/
        String url = "https://maps.google.com/maps/api/directions/" + outputFormat+ "?" + parameters;
        return url;
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(MESSAGE, "On BusTrackingActivity onStart");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(MESSAGE, "On BusTrackingActivity onRestart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(MESSAGE, "On BusTrackingActivity onResume");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(MESSAGE, "On BusTrackingActivity onStop");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(MESSAGE, "On BusTrackingActivity onPause");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i(MESSAGE, "On BusTrackingActivity onSaveInstanceState");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.i(MESSAGE, "On BusTrackingActivity onRestoreInstanceState");
    }

    @Override
    protected void onDestroy() {
        this.unregisterReceiver(broadcastReceiverBusTracking);
        super.onDestroy();
        Log.i(MESSAGE, "On BusTrackingActivity onDestroy");
    }

}