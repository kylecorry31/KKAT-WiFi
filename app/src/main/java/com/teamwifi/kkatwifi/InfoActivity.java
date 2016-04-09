package com.teamwifi.kkatwifi;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.teamwifi.kkatwifi.util.KKATWiFiHelper;

public class InfoActivity extends AppCompatActivity {

    TextView ssidText, percentText, powerText, frequencyText;
    KKATWiFiHelper wiFiHelper;
    private BroadcastReceiver mRSSIBroadcastReceiver;
    private FrameLayout indicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        wiFiHelper = new KKATWiFiHelper(this);

        ssidText = (TextView) findViewById(R.id.ssid);

        percentText = (TextView) findViewById(R.id.percent);

        powerText = (TextView) findViewById(R.id.power);

        frequencyText = (TextView) findViewById(R.id.frequency);

        indicator = (FrameLayout) findViewById(R.id.indicator);

        mRSSIBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                int signalStrength = wiFiHelper.getSignalStrength();
                ssidText.setText(wiFiHelper.getSSID());
                percentText.setText(String.valueOf(signalStrength) + "%");
                powerText.setText(String.valueOf(wiFiHelper.getRSSI()) + " dBm");
                frequencyText.setText(String.valueOf(wiFiHelper.getFrequency()) + " MHz");
                if (signalStrength >= 75)
                    indicator.setBackgroundColor(Color.rgb(245 - (signalStrength - 75) * 9, (int) (241 + (signalStrength - 75) * 0.16), 12));
                else
                    indicator.setBackgroundColor(Color.rgb((int) (219 + (signalStrength * 0.35)), (int) (59 + signalStrength * 2.43), (int) (59 - (signalStrength * 0.63))));
            }
        };
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(mRSSIBroadcastReceiver, new IntentFilter(WifiManager.RSSI_CHANGED_ACTION));
    }

    @Override
    protected void onPause() {
        unregisterReceiver(mRSSIBroadcastReceiver);
        super.onPause();
    }

}
