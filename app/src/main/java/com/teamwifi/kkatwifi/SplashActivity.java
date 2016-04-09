package com.teamwifi.kkatwifi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;

import com.teamwifi.kkatwifi.util.Settings;


/**
 * Created by kyle on 8/12/14.
 */
public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.splash);
        getWindow().setBackgroundDrawable(null);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i;
                if (Settings.isFirst(getApplicationContext())) {
                    i = new Intent(getApplicationContext(), TutorialActivity.class);
                } else {
                    i = new Intent(getApplicationContext(), MainActivity.class);
                }
                startActivity(i);
            }
        }, 1000);
    }


    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }


}
