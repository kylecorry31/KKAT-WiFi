package com.teamwifi.kkatwifibeta.util

import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.os.Build

/**
 * Created by Kyle on 7/21/2016.
 */

fun Resources.getDrawableCompat(id: Int): Drawable {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        return getDrawable(id, null)
    }
    return getDrawable(id)
}