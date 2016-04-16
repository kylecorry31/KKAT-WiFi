package com.teamwifi.kkatwifi.ui;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.teamwifi.kkatwifi.R;

/**
 * Created by Kylec on 4/2/2016.
 */
public class KKATWiFiProgressBar extends ImageView {
    public KKATWiFiProgressBar(Context context) {
        super(context);
    }

    public KKATWiFiProgressBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr){
        super(context, attrs, defStyleAttr);
    }

    public synchronized void setProgress(int progress) {
        if (progress <= 20) {
            setImageDrawable(getResources().getDrawable(R.drawable.wifi_l0));
        } else if (progress <= 40) {
            setImageDrawable(getResources().getDrawable(R.drawable.wifi_l1));
        } else if (progress <= 60) {
            setImageDrawable(getResources().getDrawable(R.drawable.wifi_l2));
        } else if (progress <= 80) {
            setImageDrawable(getResources().getDrawable(R.drawable.wifi_l3));
        } else {
            setImageDrawable(getResources().getDrawable(R.drawable.wifi_l4));
        }
    }
}
