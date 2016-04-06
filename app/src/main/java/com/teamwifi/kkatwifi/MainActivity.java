package com.teamwifi.kkatwifi;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import com.teamwifi.kkatwifi.util.KKATWiFiHelper;


public class MainActivity extends AppCompatActivity {

    private TextView mTextView;
    private KKATWiFiHelper mWifiHelper;
    private EditText mDistanceEditText;
    private BroadcastReceiver mRSSIBroadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        mTextView = (TextView) findViewById(R.id.test);
        mWifiHelper = new KKATWiFiHelper(this);
        mDistanceEditText = (EditText) findViewById(R.id.distance);
        mRSSIBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                mTextView.setText(mWifiHelper.getEfficiency(-34, 4) + "\nChannel: " + mWifiHelper.getChannel());
                Log.d("Router channels", mWifiHelper.getNearbyRouterChannels().toString());
            }
        };


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        assert fab != null;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mTextView.setText(String.valueOf(mWifiHelper.getEfficiency(-34, Double.valueOf(mDistanceEditText.getText().toString()))) +
                        "\n" + String.valueOf(mWifiHelper.getSignalStrength()));
            }
        });
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
