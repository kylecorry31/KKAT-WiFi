package com.teamwifi.kkatwifi.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;

import com.teamwifi.kkatwifi.R;

/**
 * Created by Kylec on 4/9/2016.
 */
public class Settings {
    public static boolean isFirst(Context c) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(c);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !prefs.getBoolean(c.getString(R.string.key_marshmallow), false)) {
            prefs.edit().putBoolean(c.getString(R.string.key_marshmallow), true).apply();
            return true;
        }
        return prefs.getBoolean(c.getString(R.string.key_first_time), true);
    }

    public static void setFirst(Context c, boolean first) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(c);
        prefs.edit().putBoolean(c.getString(R.string.key_first_time), first).apply();
    }
}
