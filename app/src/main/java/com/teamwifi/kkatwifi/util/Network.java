package com.teamwifi.kkatwifi.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 * Created by Kylec on 4/9/2016.
 */
public class Network {
    public static void openWebpage(Context c, String url){
        Uri webpage = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if(intent.resolveActivity(c.getPackageManager()) != null){
            c.startActivity(intent);
        }
    }
}
