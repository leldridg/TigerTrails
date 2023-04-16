package com.csci3397.tigertrails.model;

public class Path {
    String pathName;
    int rating;
    double distance;
    double minutes;

    //new path
    public Path(String pathName, double distance, double minutes) {
        this.pathName = pathName;
        this.rating = 0;
        this.distance = distance;
        this.minutes = minutes;
    }

    public String getPathName() {
        return pathName;
    }

    public int getRating() {
        return rating;
    }

    public double getDistance() {
        return distance;
    }

    public double getMinutes() {
        return minutes;
    }
    //TODO: finish
}
