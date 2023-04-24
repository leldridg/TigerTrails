package com.csci3397.tigertrails.model;

import com.google.android.gms.maps.model.LatLng;

public class Point {
    private LatLng latLng;
    private boolean isStop;
    private String desc;

    public Point(LatLng latLng) {
        this.latLng = latLng;
        this.isStop = false;
    }

    public Point(LatLng latLng, String description) {
        this.latLng = latLng;
        this.isStop = true;
        this.desc = description;
    }

    public LatLng getLatLng() {
        return latLng;
    }

    public boolean isStop() {
        return isStop;
    }

    public String getDesc() {
        return desc;
    }
}
