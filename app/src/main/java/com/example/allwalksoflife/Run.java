package com.example.shvow.finalprojactiveactiv;

import android.location.*;

/**
 * Created by shvow on 11/28/2017.
 */

public class Run {
    //fields
    int totalTime;
    float totalDistance;
    String activityType;
    //List<LatLng> routeLatLng;
    String name;
    float averagePace;

    //constructors
    //default
    public Run(){
        totalTime = 0;
        totalDistance = 0;
        activityType = "none";
        //routeLatLng = new List<LatLng>();
        name = "no name";
        averagePace = 0;
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

    public Run(int totalTime, float totalDistance, String activityType, String name, float averagePace) {
        this.totalTime = totalTime;
        this.totalDistance = totalDistance;
        this.activityType = activityType;
        this.name = name;
        this.averagePace = averagePace;
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
        return averagePace;
    }

    public void setAveragePace(float averagePace) {
        this.averagePace = averagePace;
    }

    /*
    public List<LatLng> getRouteLatLng() {
        return routeLatLng;
    }

    public void setRouteLatLng(List<LatLng> thisRoute) {
        this.routeLatLng = thisRoute;
    }

     */
}
