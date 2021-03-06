package com.example.allwalksoflife;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
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
import android.widget.Toast;

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
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;

public class CurrentRunActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private FusedLocationProviderClient mFusedLocationClient;
    private LocationRequest mLocationRequest;
    private LocationCallback mLocationCallback;
    private String TAG = "CurrentRunActivity";
    private PolylineOptions currentRoute;
    private Polyline currentRouteLine;
    private boolean finishedRunning;
    private int REQUEST_PERMISSION = 1;
    private int secondsElapsed = 0;
    private float distanceSum;
    private String activityType;

    private double distanceGoal;
    private double timeGoal;

    private boolean timeGoalReached;
    private boolean distanceGoalReached;
    private boolean running;

    public static final String RUN_KEY = "finishedRun";
    public static final String GHOST_KEY = "ghostRun";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_run);
        running = false;

        //get intent from welcome activity
        Intent intent = getIntent();
        if(intent != null){
            //get extra information out
            activityType = intent.getStringExtra("activityType");

            distanceGoal = intent.getDoubleExtra("distanceGoal",-1);
            timeGoal = intent.getDoubleExtra("timeGoal", -1);
            distanceGoalReached = false;
            timeGoalReached = false;
        }


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        currentRoute = new PolylineOptions();
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        createLocationRequest();
        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                Location lastLocation = locationResult.getLastLocation();
                if (lastLocation != null && running) {
                    double latitude = lastLocation.getLatitude();
                    double longitude = lastLocation.getLongitude();
                    LatLng position = new LatLng(latitude, longitude);
                    currentRoute.add(position);
                    currentRouteLine.setPoints(currentRoute.getPoints());
                    Log.d(TAG, "position = " + position);
                }
            }
        };

        findViewById(R.id.startStopButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!running && !finishedRunning) {
                    running = true;
                    currentRouteLine = mMap.addPolyline(currentRoute.width(5.0f).color(Color.BLUE));
                    startTimer();
                    startDistanceCalculations();
                    ((Button)findViewById(R.id.startStopButton)).setText(getString(R.string.stop));
                } else {
                    finishedRunning = true;
                    view.setEnabled(false);

                    //go to result activity
                    Intent intent = new Intent(CurrentRunActivity.this, ResultActivity.class);
                    Run finishedRun = new Run();
                    finishedRun.setActivityType(activityType);
                    finishedRun.setTotalTime(secondsElapsed);
                    finishedRun.setTotalDistance(distanceSum);
                    finishedRun.setRouteLatLng(currentRoute.getPoints());

                    intent.putExtra(RUN_KEY, finishedRun);

                    mFusedLocationClient.removeLocationUpdates(mLocationCallback);
                    startActivity(intent);
                    finish();
                }
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

        startRunLocation();
    }

    private void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(5000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    private void startRunLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            Log.d(TAG, "asking for permission");
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_PERMISSION);
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
            Intent intent = getIntent();
            if (intent.getParcelableArrayListExtra(GHOST_KEY) != null) {
                ArrayList<LatLng> ghostPoints = intent.getParcelableArrayListExtra(GHOST_KEY);
                Log.d(TAG, "Adding ghost Route: " + ghostPoints);
                PolylineOptions ghostRoute = new PolylineOptions()
                        .width(5.0f)
                        .color(Color.GREEN)
                        .addAll(ghostPoints);
                mMap.addPolyline(ghostRoute);
            }
            mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, null);
            mMap.setMyLocationEnabled(true);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Try again with new permission
                startRunLocation();
            } else if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_DENIED) {
                finish();
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

                //check to see if time goal has been completed
                //check to see if goal has been completed{

                int goalSeconds =((int)timeGoal )% 60;
                int goalMinutes = ( ( (int)timeGoal )/ 60  ) % 60;

                String goalSecondsStr =  Integer.toString(goalSeconds);
                if(goalSeconds < 10){
                    goalSecondsStr = "0" + Integer.toString(goalSeconds);
                }
                if(timeGoal != -1){
                    if(secondsElapsed >= timeGoal && timeGoalReached == false ){
                        Toast.makeText(CurrentRunActivity.this, "Congratulations! You have surpassed your " +
                                "time goal of " +goalMinutes + ":" +  goalSecondsStr +"!", Toast.LENGTH_SHORT).show();
                        timeGoalReached = true;
                    }
                }

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
                distanceSum = 0.0f;
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
                        distanceSum += (distance[0] / 1609.34f); // Convert to miles

                        //check to see if goal has been completed{
                        if(distanceGoal != -1){
                            if(distanceSum >= distanceGoal && distanceGoalReached == false ){
                                Toast.makeText(CurrentRunActivity.this, "Congratulations! You have surpassed your " +
                                        "distance goal of " + distanceGoal +"!", Toast.LENGTH_SHORT).show();
                                distanceGoalReached = true;
                            }
                        }
                    }
                    ((TextView)findViewById(R.id.distance)).setText(
                            String.format("%.2f %s", distanceSum , getString(R.string.distance_measure)));
                }
                if (!finishedRunning) distanceCalc.postDelayed(this, 5000);
            }
        }, 5000);
    }

}
