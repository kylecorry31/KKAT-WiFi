package com.teamwifi.kkatwifibeta.util

import android.content.Context
import android.os.Build
import android.preference.PreferenceManager

/**
 * Created by Kyle on 7/21/2016.
 */

object Settings {
    fun isFirst(context: Context): Boolean {
        val prefs = PreferenceManager.getDefaultSharedPreferences(context)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !prefs.getBoolean("marshmallow", false)) {
            prefs.edit {
                putBoolean("marshmallow", true)
            }
            return true
        }
        return prefs.getBoolean("firstTime", true)
    }

    fun setFirst(context: Context, first: Boolean) {
        val prefs = PreferenceManager.getDefaultSharedPreferences(context)
        prefs.edit {
            putBoolean("firstTime", first)
        }
    }
}