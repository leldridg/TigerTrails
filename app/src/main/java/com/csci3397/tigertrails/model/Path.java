package com.csci3397.tigertrails.model;

import java.util.ArrayList;

public class Path {
    private String pathName;
    private int rating;
    private double distance;
    private double minutes;

    private ArrayList<Point> points;

    //DUMMY NEW PATH
    public Path(String pathName, double distance, double minutes) {
        this.pathName = pathName;
        this.rating = 0;
        this.distance = distance;
        this.minutes = minutes;
        this.points = new ArrayList<Point>();
    }

    //new path
    public Path(String pathName, double distance, double minutes, ArrayList<Point> points) {
        this.pathName = pathName;
        this.distance = distance;
        this.minutes = minutes;
        this.points = points;
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
