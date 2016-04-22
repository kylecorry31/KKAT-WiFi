package com.teamwifi.kkatwifi;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.teamwifi.kkatwifi.util.AnalyzedLocations;
import com.teamwifi.kkatwifi.util.KKATWiFiHelper;
import com.teamwifi.kkatwifi.util.ScannedLocation;

import java.util.ArrayList;

public class ResultActivity extends AppCompatActivity {

    private TextView helperText;
    private ArrayList<ScannedLocation> badLocations;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_result);
        helperText = (TextView) findViewById(R.id.ominn);
        badLocations = new ArrayList<>();
        analyzeLocations();
    }

    private void analyzeLocations() {
        ScannedLocation router = AnalyzedLocations.get(ScannedLocation.Location.ROUTER);
        if (router == null) {
            return;
        }
        int routerRSSI = router.getAverageReading();
        String text;
        for (ScannedLocation location : AnalyzedLocations.getAll()) {
            if (location.getName().equals(ScannedLocation.Location.ROUTER.toString()) || location.getName().isEmpty()) {
                continue;
            }
            if (KKATWiFiHelper.isObjectInPath(location.getAverageReading(), routerRSSI) && !KKATWiFiHelper.isSignalStrengthGood(location.getAverageReading())) {
                badLocations.add(location);
            }
        }
        if (areAllLocationsGood()) {
            text = "All scanned locations were good! Your router is in the best position.";
        } else {
            text = "Try moving the router closer to the following locations, or if the router is already close to them, check for any metal or glass between the router and the location.";
            text += "\n";
            for (ScannedLocation badLocation : badLocations) {
                text += "\n" + badLocation.getName() + "\n";
            }
        }
        helperText.setText(text);
    }

    private boolean areAllLocationsGood() {
        return badLocations.isEmpty();
    }


}
