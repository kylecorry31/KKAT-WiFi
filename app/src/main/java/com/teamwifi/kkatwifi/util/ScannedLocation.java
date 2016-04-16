package com.teamwifi.kkatwifi.util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kylec on 4/9/2016.
 */
public class ScannedLocation {

    public enum Location {
        ROUTER, FRONT, LEFT, RIGHT, BACK
    }

    private String name;
    private Location location;
    private List<Integer> readings;

    public ScannedLocation(String locationName) {
        name = locationName;
        readings = new ArrayList<>();
    }

    public ScannedLocation(Location location) {
        this.location = location;
        readings = new ArrayList<>();
    }

    public void addRSSI(int rssi) {
        readings.add(rssi);
    }

    public double getAverageReading() {
        double sum = 0;
        for (int value : readings)
            sum += value;
        return sum / readings.size();
    }

    public String getName() {
        if (name != null)
            return name;
        if (location != null)
            return location.toString();
        return "";
    }

    public Location getLocation() {
        if (location != null)
            return location;
        return null;
    }
}
