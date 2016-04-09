package com.teamwifi.kkatwifi;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.TextView;

import com.teamwifi.kkatwifi.util.KKATWiFiHelper;
import com.teamwifi.kkatwifi.util.Network;
import com.teamwifi.kkatwifi.util.Settings;


public class MainActivity extends AppCompatActivity {

    private TextView mTextView;
    private KKATWiFiHelper mWifiHelper;
    private BroadcastReceiver mRSSIBroadcastReceiver;
    private ImageButton getStarted, info, learn, url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main);
        

        mTextView = (TextView) findViewById(R.id.test);
        mWifiHelper = new KKATWiFiHelper(this);
        mRSSIBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                mTextView.setText(mWifiHelper.getEfficiency(-34, 4) + "\nChannel: " + mWifiHelper.getChannel());
                Log.d("Router channels", mWifiHelper.getNearbyRouterChannels().toString());
            }
        };

        getStarted = (ImageButton) findViewById(R.id.get_started);
        learn = (ImageButton) findViewById(R.id.learn);
        info = (ImageButton) findViewById(R.id.info);
        url = (ImageButton) findViewById(R.id.url);


        getStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getStarted.setImageDrawable(getResources().getDrawable(R.drawable.get_started_pressed));
                startActivity(new Intent(getApplicationContext(), AnalysisActivity.class));
            }
        });

        learn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                learn.setImageDrawable(getResources().getDrawable(R.drawable.learn_pressed));
                startActivity(new Intent(getApplicationContext(), LearnActivity.class));
            }
        });

        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                info.setImageDrawable(getResources().getDrawable(R.drawable.info_pressed));
                startActivity(new Intent(getApplicationContext(), InfoActivity.class));
            }
        });

        url.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                url.setImageDrawable(getResources().getDrawable(R.drawable.url_pressed));
                Network.openWebpage(getApplicationContext(), "http://kylecorry31.github.io/KKAT-WiFi-Test");
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(mRSSIBroadcastReceiver, new IntentFilter(WifiManager.RSSI_CHANGED_ACTION));
        getStarted.setImageDrawable(getResources().getDrawable(R.drawable.get_started_unpressed));
        info.setImageDrawable(getResources().getDrawable(R.drawable.info_unpressed));
        learn.setImageDrawable(getResources().getDrawable(R.drawable.learn_unpressed));
        url.setImageDrawable(getResources().getDrawable(R.drawable.url_unpressed));
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
        } else if (id == R.id.action_tutorial) {
            startActivity(new Intent(this, TutorialActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
