package com.csci3397.tigertrails.model;

import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;

public class Point implements Serializable {

    private double latitude;

    private double longitude;
    //private LatLng latLng;
    private String desc;

    //No-argument constructor
    public Point() {}

    public Point(LatLng latLng) {
        this.latitude = latLng.latitude;
        System.out.println(latitude);
        this.longitude = latLng.longitude;
        System.out.println(longitude);
    }

    public Point(LatLng latLng, String description) {
        this.latitude = latLng.latitude;
        this.longitude = latLng.longitude;
        this.desc = description;
    }

    public Point(LatLng latLng, String description, boolean isStop) {
        this.latitude = latLng.latitude;
        this.longitude = latLng.longitude;
        this.desc = description;
    }

    public LatLng getLatLng() {
        return new LatLng(latitude,longitude);
    }

    public String getDesc() {
        return desc;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public boolean isStop() {
        return !(desc == null);
    }
}
