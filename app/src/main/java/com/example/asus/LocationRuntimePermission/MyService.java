package com.example.asus.LocationRuntimePermission;

import android.Manifest;
import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;

public class MyService extends Service implements ActivityCompat.OnRequestPermissionsResultCallback {

    Context context;
    private final String TAG = MyService.class.getSimpleName();
    CurrentLocation currentlocation;
    LocationManager locationmanager;
    public int REQUEST_LOCATION = 0;
    Activity act;

    public MyService() {
    }

    public MyService(MainActivity mainActivity) {

        act = mainActivity;
    }

    public class LocationRunnable implements Runnable {

        @Override
        public void run() {

        /*
         * Code you want to run on the thread goes here
         */
            Log.v(TAG, "LocationRunnable");
            locationmanager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            currentlocation = new CurrentLocation();

            initiatGPStracker();
        }

    }

    @Override
    public void onCreate() {


        LocationRunnable lr = new LocationRunnable();
        Thread t1 = new Thread(lr);
        t1.start();
        Log.v(TAG, "oncreate");

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


        Log.v(TAG, "onStartCommand");
//        currentlocation.onProviderDisabled(locationmanager.GPS_PROVIDER);

        return START_STICKY;


    }


    public void initiatGPStracker() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {


                ActivityCompat.requestPermissions(new Activity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
            }
        }
        locationmanager.requestLocationUpdates(locationmanager.GPS_PROVIDER, 15000, 0, currentlocation);
        Log.v(TAG, "initiatGPStracker");

    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {


        if (requestCode == REQUEST_LOCATION) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                Log.v("MainActivity", "Location permission has now been granted. Showing preview.");

            } else {
                Log.v("MainActivity", "Location permission was NOT granted.");

            }
        }

    }


    public class CurrentLocation implements LocationListener {

        @Override
        public void onLocationChanged(Location location) {
            Log.v(TAG, "onLocationChanged");
            Toast.makeText(getApplicationContext(), "L" + location.getLatitude() + " : " + location.getLongitude(), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {


        }
    }

}
