package com.example.allwalksoflife;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;

public class Run implements Parcelable{
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


    protected Run(Parcel in) {
        totalTime = in.readInt();
        totalDistance = in.readFloat();
        activityType = in.readString();
        routeLatLng = in.createTypedArrayList(LatLng.CREATOR);
        name = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(totalTime);
        dest.writeFloat(totalDistance);
        dest.writeString(activityType);
        dest.writeTypedList(routeLatLng);
        dest.writeString(name);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Run> CREATOR = new Creator<Run>() {
        @Override
        public Run createFromParcel(Parcel in) {
            return new Run(in);
        }

        @Override
        public Run[] newArray(int size) {
            return new Run[size];
        }
    };

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
        if (totalTime != 0) {
            return totalDistance / (totalTime / 3600.0f);
        } else {
            return 0.0f;
        }
    }

    public List<LatLng> getRouteLatLng() {
        return routeLatLng;
    }

    public void setRouteLatLng(List<LatLng> thisRoute) {
        this.routeLatLng = thisRoute;
    }

}
