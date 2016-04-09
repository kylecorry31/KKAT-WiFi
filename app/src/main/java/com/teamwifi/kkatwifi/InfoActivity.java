package com.teamwifi.kkatwifi;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.teamwifi.kkatwifi.util.KKATWiFiHelper;

import org.w3c.dom.Text;

public class InfoActivity extends AppCompatActivity {

    TextView ssidText, percentText, powerText, frequencyText, channelText;
    KKATWiFiHelper wiFiHelper;
    //    private BroadcastReceiver mRSSIBroadcastReceiver;
    private LinearLayout indicator;
    private Handler handler;
    private Runnable runnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_info);

        wiFiHelper = new KKATWiFiHelper(this);

        ssidText = (TextView) findViewById(R.id.ssid);

        percentText = (TextView) findViewById(R.id.percent);

        powerText = (TextView) findViewById(R.id.power);

        frequencyText = (TextView) findViewById(R.id.frequency);

        channelText = (TextView) findViewById(R.id.channel);

        indicator = (LinearLayout) findViewById(R.id.infoLayout);

        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                updateInfo();
            }
        };

//        mRSSIBroadcastReceiver = new BroadcastReceiver() {
//            @Override
//            public void onReceive(Context context, Intent intent) {
//                updateInfo();
//            }
//        };
    }

    private void updateInfo() {
        int signalStrength = wiFiHelper.getSignalStrength();
        ssidText.setText(wiFiHelper.getSSID());
        percentText.setText(String.valueOf(signalStrength) + "%");
        powerText.setText(String.valueOf(wiFiHelper.getRSSI()) + " dBm");
        frequencyText.setText(String.valueOf(wiFiHelper.getFrequency()) + " MHz");
        channelText.setText("Channel " + String.valueOf(wiFiHelper.getChannel()));
        if (signalStrength >= 75)
            indicator.setBackgroundColor(Color.rgb(245 - (signalStrength - 75) * 9, (int) (241 + (signalStrength - 75) * 0.16), 12));
        else
            indicator.setBackgroundColor(Color.rgb((int) (219 + (signalStrength * 0.35)), (int) (59 + signalStrength * 2.43), (int) (59 - (signalStrength * 0.63))));
        handler.postDelayed(runnable, 100);
    }

    @Override
    protected void onResume() {
        super.onResume();
//        registerReceiver(mRSSIBroadcastReceiver, new IntentFilter(WifiManager.RSSI_CHANGED_ACTION));
        handler.post(runnable);
    }

    @Override
    protected void onPause() {
//        unregisterReceiver(mRSSIBroadcastReceiver);
        handler.removeCallbacks(runnable);
        super.onPause();
    }

}
