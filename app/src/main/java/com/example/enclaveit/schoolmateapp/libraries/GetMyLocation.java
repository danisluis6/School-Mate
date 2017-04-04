package com.example.enclaveit.schoolmateapp.libraries;

import android.app.Service;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by enclaveit on 22/03/2017.
 */

public class GetMyLocation {
    Context context;
    public GetMyLocation(Context context){  this.context = context; }

    public LatLng getMyPosition() {
        LocationManager locationManager = (LocationManager) this.context.getSystemService(Service.LOCATION_SERVICE);
        //Creating criteria object ti retrieve provider
        Criteria criteria = new Criteria();
        //Getting name of best provider
        String provider = locationManager.getBestProvider(criteria, true);
        //Getting current location
            /*Check permission*/
        if (ActivityCompat.checkSelfPermission(this.context, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this.context, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
        }
        Location location = locationManager.getLastKnownLocation(provider);
        if(location != null){
            return new LatLng(location.getLatitude(), location.getLongitude());
        }
            /*If can not get my location, get the default location*/
        Log.i("Get My Location", "Cannot get my position, return default position");
        return null; //new LatLng(16.053324, 108.217704);
    }
}
