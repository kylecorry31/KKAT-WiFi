package com.teamwifi.kkatwifi.util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kylec on 4/9/2016.
 */
public class AnalyzedLocations {

    private List<ScannedLocation> scannedLocations = new ArrayList<>();

    public void add(ScannedLocation location) {
        scannedLocations.add(location);
    }

    public void clear() {
        scannedLocations.clear();
    }

    public ScannedLocation get(int location) {
        return scannedLocations.get(location);
    }

    public ScannedLocation get(ScannedLocation.Location location) {
        for (ScannedLocation scannedLocation : scannedLocations)
            if (scannedLocation.getLocation().equals(location))
                return scannedLocation;
        return null;
    }

    public ScannedLocation get(String name) {
        for (ScannedLocation scannedLocation : scannedLocations)
            if (scannedLocation.getName().equals(name))
                return scannedLocation;
        return null;
    }

    public int find(ScannedLocation location) {
        return scannedLocations.indexOf(location);
    }

    public void remove(int location) {
        scannedLocations.remove(location);
    }

    public void remove(ScannedLocation location) {
        scannedLocations.remove(location);
    }

    public void remove(ScannedLocation.Location location) {
        scannedLocations.remove(get(location));
    }

    public void remove(String name) {
        scannedLocations.remove(get(name));
    }

}
