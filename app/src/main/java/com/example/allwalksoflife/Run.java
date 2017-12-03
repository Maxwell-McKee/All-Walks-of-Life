package com.example.allwalksoflife;

import android.location.*;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;

/**
 * Created by shvow on 11/28/2017.
 */

public class Run {
    //fields
    private int totalTime;
    private float totalDistance;
    private String activityType;
    private List<LatLng> routeLatLng;
    private String name;

    //constructors
    //default
    public Run(){
        totalTime = 0;
        totalDistance = 0;
        activityType = "none";
        //routeLatLng = new List<LatLng>();
        name = "no name";
    }

    //explicit
    /*
    public Run(int totalTime, float totalDistance, List<LatLng> list, String activityType, String name, float averagePace) {
        this.totalTime = totalTime;
        this.totalDistance = totalDistance;
        this.activityType = activityType;
        //routeLatLng = list;
        this.name = name;
        this.averagePace = averagePace;
    }
    */

    public Run(int totalTime, float totalDistance, String activityType, String name) {
        this.totalTime = totalTime;
        this.totalDistance = totalDistance;
        this.activityType = activityType;
        this.name = name;
    }


    public int getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(int totalTime) {
        this.totalTime = totalTime;
    }

    public float getTotalDistance() {
        return totalDistance;
    }

    public void setTotalDistance(float totalDistance) {
        this.totalDistance = totalDistance;
    }

    public String getActivityType() {
        return activityType;
    }

    public void setActivityType(String activityType) {
        this.activityType = activityType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getAveragePace() {
        return totalDistance / (totalTime / 3600.0f);
    }

    public List<LatLng> getRouteLatLng() {
        return routeLatLng;
    }

    public void setRouteLatLng(List<LatLng> thisRoute) {
        this.routeLatLng = thisRoute;
    }

}
