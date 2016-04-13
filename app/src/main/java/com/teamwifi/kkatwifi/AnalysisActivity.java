package com.teamwifi.kkatwifi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.teamwifi.kkatwifi.util.AnalyzedLocations;
import com.teamwifi.kkatwifi.util.KKATWiFiHelper;
import com.teamwifi.kkatwifi.util.ScannedLocation;

public class AnalysisActivity extends AppCompatActivity {

    private Button continueButton;

    private TextView instructionText;

    private ScannedLocation.Location currentState;

    private KKATWiFiHelper kkatWiFiHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_analysis);

        kkatWiFiHelper = new KKATWiFiHelper(this);

        AnalyzedLocations.clear();

        instructionText = (TextView) findViewById(R.id.instruction);

        currentState = ScannedLocation.Location.ROUTER;

        continueButton = (Button) findViewById(R.id.continue_button);
        displayInstructions(currentState);

        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(getApplicationContext(), ResultActivity.class));
                ScannedLocation location = new ScannedLocation(currentState);
                location.addRSSI(kkatWiFiHelper.getRSSI());
                AnalyzedLocations.add(location);
                if (!nextState()) {
                    startActivity(new Intent(getApplicationContext(), ResultActivity.class));
                }
                displayInstructions(currentState);
            }
        });
    }

    private void displayInstructions(ScannedLocation.Location state) {
        instructionText.setText(state.toString());
    }


    private boolean nextState() {
        switch (currentState) {
            case ROUTER:
                currentState = ScannedLocation.Location.FRONT;
                break;
            case FRONT:
                currentState = ScannedLocation.Location.LEFT;
                break;
            case LEFT:
                currentState = ScannedLocation.Location.BACK;
                break;
            case BACK:
                currentState = ScannedLocation.Location.RIGHT;
                break;
            case RIGHT:
                return false;
        }
        return true;
    }

}
