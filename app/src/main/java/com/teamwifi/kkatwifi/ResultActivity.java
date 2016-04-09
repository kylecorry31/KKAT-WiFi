package com.teamwifi.kkatwifi;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.teamwifi.kkatwifi.lann.NeuralNetwork;
import com.teamwifi.kkatwifi.lann.activation.Linear;
import com.teamwifi.kkatwifi.lann.activation.Sigmoid;
import com.teamwifi.kkatwifi.lann.activation.Softmax;

public class ResultActivity extends AppCompatActivity {

    private NeuralNetwork net;
    private TextView ominnText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_result);
        ominnText = (TextView) findViewById(R.id.ominn);
        createOMINN();
    }

    private void createOMINN() {
        net = new NeuralNetwork.Builder()
                .addLayer(1, 3, new Linear())
                .addLayer(3, 4, new Sigmoid())
                .addLayer(4, 4, new Softmax())
                .build();
        ominnText.setText("OMINN Created");
    }

}
