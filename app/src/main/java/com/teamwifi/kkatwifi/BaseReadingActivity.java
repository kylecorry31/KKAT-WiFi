package com.teamwifi.kkatwifi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.teamwifi.kkatwifi.util.AnalyzedLocations;
import com.teamwifi.kkatwifi.util.KKATWiFiHelper;
import com.teamwifi.kkatwifi.util.ScannedLocation;

public class BaseReadingActivity extends AppCompatActivity {

    private KKATWiFiHelper kkatWiFiHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_analysis_router);

        kkatWiFiHelper = new KKATWiFiHelper(this);

        AnalyzedLocations.clear();

        Button continueButton = (Button) findViewById(R.id.continue_button);

        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ScannedLocation location = new ScannedLocation(ScannedLocation.Location.ROUTER);
                for (int i = 0; i < 10; i++)
                    location.addRSSI(kkatWiFiHelper.getRSSI());
                AnalyzedLocations.add(location);
                startActivity(new Intent(getApplicationContext(), ScanLocationActivity.class));
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
