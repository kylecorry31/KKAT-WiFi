package com.teamwifi.kkatwifi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.teamwifi.kkatwifi.util.AnalyzedLocations;
import com.teamwifi.kkatwifi.util.KKATWiFiHelper;
import com.teamwifi.kkatwifi.util.ScannedLocation;

public class ScanLocationActivity extends AppCompatActivity {

    private Button continueButton, doneButton;

    private EditText locationNameEdit;

    private KKATWiFiHelper kkatWiFiHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_analysis_user_location);

        kkatWiFiHelper = new KKATWiFiHelper(this);

        continueButton = (Button) findViewById(R.id.continue_button);

        doneButton = (Button) findViewById(R.id.noMoreButton);

        locationNameEdit = (EditText) findViewById(R.id.locationText);

        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (locationNameEdit.getText().toString().isEmpty()) {
                    locationNameEdit.setError("Must name location!");
                    return;
                }
                ScannedLocation location = new ScannedLocation(locationNameEdit.getText().toString());
                for (int i = 0; i < 10; i++)
                    location.addRSSI(kkatWiFiHelper.getRSSI());
                AnalyzedLocations.add(location);
                locationNameEdit.setText("");
                Toast.makeText(getApplicationContext(), "Location Analyzed", Toast.LENGTH_SHORT).show();
            }
        });

        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ResultActivity.class));
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
