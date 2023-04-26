package com.csci3397.tigertrails.model;

import com.google.android.gms.maps.model.LatLng;

public class Point {

    private double latitude;

    private double longitude;
    //private LatLng latLng;
    private boolean isStop;
    private String desc;

    //No-argument constructor
    public Point() {}

    public Point(LatLng latLng) {
        this.latitude = latLng.latitude;
        this.longitude = latLng.longitude;
        this.isStop = false;
    }

    public Point(LatLng latLng, String description) {
        this.latitude = latLng.latitude;
        this.longitude = latLng.longitude;
        this.isStop = true;
        this.desc = description;
    }

    public LatLng getLatLng() {
        return new LatLng(latitude,longitude);
    }

    public boolean isStop() {
        return isStop;
    }

    public String getDesc() {
        return desc;
    }
}
