package com.teamwifi.kkatwifi.util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kylec on 4/9/2016.
 */
public class AnalyzedLocations {

    private static List<ScannedLocation> scannedLocations = new ArrayList<>();

    public static void add(ScannedLocation location) {
        scannedLocations.add(location);
    }

    public static void clear() {
        scannedLocations.clear();
    }

    public static ScannedLocation get(int location) {
        return scannedLocations.get(location);
    }

    public static List<ScannedLocation> getAll() {
        return scannedLocations;
    }

    public static ScannedLocation get(ScannedLocation.Location location) {
        for (ScannedLocation scannedLocation : scannedLocations)
            if (scannedLocation.getLocation().equals(location))
                return scannedLocation;
        return null;
    }

    public static ScannedLocation get(String name) {
        for (ScannedLocation scannedLocation : scannedLocations)
            if (scannedLocation.getName().equals(name))
                return scannedLocation;
        return null;
    }

    public static int find(ScannedLocation location) {
        return scannedLocations.indexOf(location);
    }

    public static void remove(int location) {
        scannedLocations.remove(location);
    }

    public static void remove(ScannedLocation location) {
        scannedLocations.remove(location);
    }

    public static void remove(ScannedLocation.Location location) {
        scannedLocations.remove(get(location));
    }

    public static void remove(String name) {
        scannedLocations.remove(get(name));
    }

}
