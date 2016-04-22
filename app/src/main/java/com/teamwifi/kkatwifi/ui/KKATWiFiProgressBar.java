package com.teamwifi.kkatwifi.ui;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.teamwifi.kkatwifi.R;
import com.teamwifi.kkatwifi.util.Resources;

/**
 * Created by Kylec on 4/2/2016.
 */
public class KKATWiFiProgressBar extends ImageView {
    public KKATWiFiProgressBar(Context context) {
        super(context);
    }

    public KKATWiFiProgressBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public synchronized void setProgress(int progress) {
        if (progress <= 20) {
            setImageDrawable(Resources.getDrawable(getContext(), R.drawable.wifi_l0));
        } else if (progress <= 40) {
            setImageDrawable(Resources.getDrawable(getContext(), R.drawable.wifi_l1));
        } else if (progress <= 60) {
            setImageDrawable(Resources.getDrawable(getContext(), R.drawable.wifi_l2));
        } else if (progress <= 80) {
            setImageDrawable(Resources.getDrawable(getContext(), R.drawable.wifi_l3));
        } else {
            setImageDrawable(Resources.getDrawable(getContext(), R.drawable.wifi_l4));
        }
    }
}
