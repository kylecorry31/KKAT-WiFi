package com.teamwifi.kkatwifi;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.teamwifi.kkatwifi.lann.Matrix;
import com.teamwifi.kkatwifi.lann.NeuralNetwork;
import com.teamwifi.kkatwifi.lann.activation.Linear;
import com.teamwifi.kkatwifi.lann.activation.Sigmoid;
import com.teamwifi.kkatwifi.lann.activation.Softmax;

import java.util.Arrays;

public class ResultActivity extends AppCompatActivity {

    private NeuralNetwork net;
    private TextView ominnText;

    private enum Obstruction {
        METAL, GLASS, WALL, UNKNOWN;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_result);
        ominnText = (TextView) findViewById(R.id.ominn);
        createOMINN();
        displayObstruction(0); // TODO: get values from Intent
    }

    private void createOMINN() {
        net = new NeuralNetwork.Builder()
                .addLayer(1, 3, new Linear())
                .addLayer(3, 4, new Sigmoid())
                .addLayer(4, 4, new Softmax())
                .build();
        ominnText.setText("OMINN Created");
    }

    public void displayObstruction(double value) {
        Matrix netOutput = net.predict(new Matrix(new double[][]{{value}}));
        String obstructionText = "Obstruction Material Prediction\n(experimental)\n\n";
        for (int row = 0; row < netOutput.getNumRows(); row++)
            for (int col = 0; col < netOutput.getNumCols(); col++)
                obstructionText += Obstruction.values()[row] + " " + Math.round(netOutput.get(row, col) * 100) + "%\n";
        ominnText.setText(obstructionText);
    }

}
