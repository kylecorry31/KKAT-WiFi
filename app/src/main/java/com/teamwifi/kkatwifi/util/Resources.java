package com.teamwifi.kkatwifi.util;

import android.content.Context;
import android.graphics.drawable.Drawable;

/**
 * Created by Kylec on 4/22/2016.
 */
public class Resources {

    public static Drawable getDrawable(Context c, int id) {
        Drawable drawable;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            drawable = c.getResources().getDrawable(id, null);
        } else {
            drawable = c.getResources().getDrawable(id);
        }
        return drawable;
    }
}
