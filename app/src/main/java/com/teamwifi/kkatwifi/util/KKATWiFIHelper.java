package com.teamwifi.kkatwifi.util;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Build;

import java.util.List;

/**
 * Created by Kylec on 4/2/2016.
 */
public class KKATWiFiHelper {

    private WifiManager mWifiManager;

    public KKATWiFiHelper(Context context) {
        mWifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
    }

    /**
     * Gets the current RSSI (Relative Signal Strength Indicator) of the WiFi connection.
     *
     * @return The RSSI of the connection in dB.
     */
    public int getRSSI() {
        return mWifiManager.getConnectionInfo().getRssi();
    }

    /**
     * Gets the current frequency of the WiFi connection.
     *
     * @return The frequency of the connection in GHz.
     */
    public double getFrequency() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return mWifiManager.getConnectionInfo().getFrequency();
        } else {
            return 2.4;
        }
    }

    /**
     * Gets the current link speed of the WiFi connection.
     *
     * @return The link speed of the connection in Mbps;
     */
    public double getLinkSpeed() {
        return mWifiManager.getConnectionInfo().getLinkSpeed();
    }

    /**
     * Gets the current SSID of the WiFI connection.
     *
     * @return The SSID of the connection.
     */
    public String getSSID() {
        return mWifiManager.getConnectionInfo().getSSID();
    }

    /**
     * Gets the current signal strength of the WiFi connection.
     *
     * @return The signal strength of the connection.
     */
    public int getSignalStrength() {
        return WifiManager.calculateSignalLevel(getRSSI(), 100);
    }

    /**
     * Gets the efficiency of the current WiFi connection at a distance.
     *
     * @param rssi1Meter       The RSSI at 1 meter.
     * @param distanceToRouter The current distance to the router.
     * @return The alpha value of the signal.
     */
    public double getEfficiency(int rssi1Meter, double distanceToRouter) {
        return (-getRSSI() + rssi1Meter) / (10 * Math.log10(distanceToRouter));
    }

    /**
     * Determines if there is an object in between the device and router which is causing a drop in signal efficiency.
     *
     * @return true if there is an object in the way.
     */
    public boolean isObjectInPath() {
        // TODO: Object detection
        return false;
    }

    public List<ScanResult> getScanResults() {
        return mWifiManager.getScanResults();
    }

}
