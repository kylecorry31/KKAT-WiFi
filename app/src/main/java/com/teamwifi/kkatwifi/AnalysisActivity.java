package com.teamwifi.kkatwifi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.teamwifi.kkatwifi.ui.KKATWiFiProgressBar;
import com.teamwifi.kkatwifi.util.AnalyzedLocations;
import com.teamwifi.kkatwifi.util.KKATWiFiHelper;
import com.teamwifi.kkatwifi.util.ScannedLocation;

public class AnalysisActivity extends AppCompatActivity {

    private Button continueButton, doneButton;

    private TextView instructionText, roomLabel;

    private EditText locationEditText;

    private ScannedLocation.Location currentState;

    private enum State {
        ROUTER, USER
    }

    private State state;

    private KKATWiFiHelper kkatWiFiHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_analysis);

        kkatWiFiHelper = new KKATWiFiHelper(this);

        AnalyzedLocations.clear();

        instructionText = (TextView) findViewById(R.id.instruction);
        locationEditText = (EditText) findViewById(R.id.location_edit);
        roomLabel = (TextView) findViewById(R.id.room_name_label);

//        currentState = ScannedLocation.Location.ROUTER;
        state = State.ROUTER;
        continueButton = (Button) findViewById(R.id.continue_button);
        doneButton = (Button) findViewById(R.id.done_button);
        displayInstructions(state);

        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(getApplicationContext(), ResultActivity.class));
                ScannedLocation location;
                if (state.equals(State.ROUTER)) {
                    location = new ScannedLocation(ScannedLocation.Location.ROUTER);
                } else {
                    location = new ScannedLocation(locationEditText.getText().toString());

                }
                roomLabel.setVisibility(View.VISIBLE);
                locationEditText.setVisibility(View.VISIBLE);
                locationEditText.setText("");
                for (int i = 0; i < 10; i++)
                    location.addRSSI(kkatWiFiHelper.getRSSI());
                AnalyzedLocations.add(location);
                state = State.USER;
                displayInstructions(state);
            }
        });

        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ScannedLocation location;
                if (state.equals(State.ROUTER)) {
                    location = new ScannedLocation(ScannedLocation.Location.ROUTER);
                } else {
                    location = new ScannedLocation(locationEditText.getText().toString());

                }
                location.addRSSI(kkatWiFiHelper.getRSSI());
                AnalyzedLocations.add(location);
                startActivity(new Intent(getApplicationContext(), ResultActivity.class));
            }
        });
    }

    private void displayInstructions(State state) {
//        instructionText.setText(state.toString());
        if (state.equals(State.ROUTER)) {
            instructionText.setText("Place your phone about 3 feet from your router, then press 'Next Reading'");
        } else {
            instructionText.setText("Move to a location in your house, set the name of the location, then press 'Next Reading' or 'Done'");
        }
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
