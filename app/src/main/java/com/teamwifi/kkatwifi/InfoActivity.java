package com.teamwifi.kkatwifi;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.teamwifi.kkatwifi.util.KKATWiFiHelper;

public class InfoActivity extends AppCompatActivity {

    TextView ssidText, percentText, powerText, frequencyText;
    KKATWiFiHelper wiFiHelper;
    private BroadcastReceiver mRSSIBroadcastReceiver;

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

        mRSSIBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                ssidText.setText(wiFiHelper.getSSID());
                percentText.setText(String.valueOf(wiFiHelper.getSignalStrength())+"%");
                powerText.setText(String.valueOf(wiFiHelper.getRSSI())+" dBm");
                frequencyText.setText(String.valueOf(wiFiHelper.getFrequency())+" MHz");
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
