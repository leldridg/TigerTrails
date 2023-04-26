package com.csci3397.tigertrails.model;

import java.util.ArrayList;
import java.io.Serializable;

public class Path implements Serializable {

    //private int pathID;
    private String pathName;

    private String creator;

    private String description;
    private int rating;
    private double distance;
    private double minutes;

    private ArrayList<Point> points;

    private boolean bookmarked;
    //this is what bookmarked would be if multiple users were using the app
    //private ArrayList<String> bookmarkedBy;

    //no-argument constructor
    public Path() {}

    //new path
    public Path(String creator, String pathName, String description, double distance, double minutes, ArrayList<Point> points) {
        this.creator = creator;
        this.pathName = pathName;
        this.distance = distance;
        this.minutes = minutes;
        this.points = points;
        this.description = description;
        this.rating = 0;
        this.bookmarked = false;
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

    public String getDescription() {
        return description;
    }

    public ArrayList<Point> getPoints() {
        return points;
    }
    //TODO: finish
}
