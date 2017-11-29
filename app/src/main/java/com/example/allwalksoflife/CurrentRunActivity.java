package com.example.allwalksoflife;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnSuccessListener;

public class CurrentRunActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private FusedLocationProviderClient mFusedLocationClient;
    private LocationRequest mLocationRequest;
    private LocationCallback mLocationCallback;
    private String TAG = "CurrentRunActivity";
    private PolylineOptions currentRoute;
    private boolean finishedRunning;
    private int REQUEST_PERMISSION = 1;
    private int secondsElapsed = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_run);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        currentRoute = new PolylineOptions();
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        createLocationRequest();
        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                Location lastLocation = locationResult.getLastLocation();
                if (lastLocation != null) {
                    double latitude = lastLocation.getLatitude();
                    double longitude = lastLocation.getLongitude();
                    LatLng position = new LatLng(latitude, longitude);
                    currentRoute.add(position);
                    Log.d(TAG, "position = " + position);
                }
            }
        };

        findViewById(R.id.startStopButton).setOnClickListener(new View.OnClickListener() {
            private boolean running = false;
            @Override
            public void onClick(View view) {
                if(!running && !finishedRunning) {
                    running = true;
                    startTimer();
                    startDistanceCalculations();
                    ((Button)findViewById(R.id.startStopButton)).setText(getString(R.string.stop));
                } else {
                    finishedRunning = true;
                    view.setEnabled(false);
                }
                mMap.addPolyline(currentRoute.width(5.0f).color(Color.BLUE));
            }
        });
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        getLastLocation();
        startLocationUpdates();
        mMap.setMyLocationEnabled(true);
    }

    private void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(5000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    private void getLastLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            Log.d(TAG, "asking for permission");
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_PERMISSION);
        } else {
            mFusedLocationClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if (location != null) {
                        double latitude = location.getLatitude();
                        double longitude = location.getLongitude();
                        LatLng position = new LatLng(latitude, longitude);
                        currentRoute.add(position);
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(position));
                        mMap.moveCamera(CameraUpdateFactory.zoomTo(15.0f));
                    }
                }
            });
        }
    }

    @SuppressLint("MissingPermission")
    private void startLocationUpdates() {
        mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, null);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Try again with new permission
                getLastLocation();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void startTimer() {
        final Handler timer = new Handler();
        timer.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!finishedRunning) secondsElapsed++;
                int seconds = secondsElapsed % 60;
                int minutes = (secondsElapsed / 60) % 60;
                int hours = (secondsElapsed / 3600);
                String time = String.format("%d:%02d:%02d", hours, minutes, seconds);

                ((TextView)findViewById(R.id.timeElapsed)).setText(time);
                timer.postDelayed(this, 1000);
            }
        }, 1000);
    }

    private void startDistanceCalculations() {
        final Handler distanceCalc = new Handler();
        distanceCalc.postDelayed(new Runnable() {
            @Override
            public void run() {
                float distanceSum = 0.0f;
                float[] distance = new float[1];
                LatLng latLng1 = currentRoute.getPoints().get(0);
                if (currentRoute.getPoints().size() > 1) {
                    for (int i = 1; i < currentRoute.getPoints().size(); i++) {
                        LatLng latLng2 = currentRoute.getPoints().get(i);
                        Location.distanceBetween(
                                latLng1.latitude,
                                latLng1.longitude,
                                latLng2.latitude,
                                latLng2.longitude,
                                distance
                        );
                        latLng1 = latLng2;
                        distanceSum += distance[0];
                    }
                    ((TextView)findViewById(R.id.distance)).setText("" + distanceSum + " m");
                }
                if (!finishedRunning) distanceCalc.postDelayed(this, 5000);
            }
        }, 5000);
    }
}
