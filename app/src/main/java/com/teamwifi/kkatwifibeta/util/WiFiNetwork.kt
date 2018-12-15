package com.teamwifi.kkatwifibeta.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.wifi.ScanResult
import android.net.wifi.WifiManager
import android.os.Build


class WiFiNetwork(context: Context) {
    private val mWiFiManager = context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
    private val mConnectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    companion object {
        fun isObjectInPath(rssi1: Int, rssi2: Int): Boolean = Math.abs(rssi1 - rssi2) > 30
        fun isSignalStrengthGood(rssi1: Int): Boolean = WifiManager.calculateSignalLevel(rssi1, 101) > 70
        /**
         * 7 point scale, 7 is great, 1 is very bad
         */
        fun calculateRSSIQuality(rssi1: Int): Int {
            return when {
                rssi1 >= -30 -> 7 // Max strength
                rssi1 >= -50 -> 6 // Excellent
                rssi1 >= -60 -> 5 // Good
                rssi1 >= -67 -> 4 // Weak but reliable
                rssi1 >= -70 -> 3 // Weak, not reliable
                rssi1 >= -80 -> 2 // Bad
                else -> 1 // Terrible
            }
        }

        fun describeRSSIQuality(rssi1: Int): String {
            val quality = calculateRSSIQuality(rssi1)
            return when (quality) {
                7, 6 -> "Excellent signal detected"
                5, 4 -> "Good signal detected"
                3 -> "Weak signal detected"
                else -> "Very bad signal detected"
            }
        }

        fun calculateChannel(freq: Int): Int {
            if (freq == 2484)
                return 14
            if (freq < 2484)
                return (freq - 2407) / 5
            return freq / 5 - 1000
        }
    }

    val isConnected: Boolean
        get() {
            val activeNetworkInfo = mConnectivityManager.activeNetworkInfo
            return activeNetworkInfo != null && activeNetworkInfo.type == ConnectivityManager.TYPE_WIFI && activeNetworkInfo.isConnectedOrConnecting
        }

    val channel: Int
        get() = calculateChannel(frequency)

    val rssi: Int
        get() = mWiFiManager.connectionInfo.rssi

    val frequency: Int
        get() {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                return mWiFiManager.connectionInfo.frequency
            } else {
                return 2407
            }
        }

    val linkSpeed: Int
        get() = mWiFiManager.connectionInfo.linkSpeed

    val ssid: String
        get() = mWiFiManager.connectionInfo.ssid.replace("\"", "")

    val signalStrength: Int
        get() = WifiManager.calculateSignalLevel(rssi, 101)

    val scanResults: List<ScanResult>
        get() = mWiFiManager.scanResults

    val nearbyNetworkChannels: List<Int>
        get() {
            val nearbyChannels: MutableList<Int> = mutableListOf()
            for (scanResult in scanResults)
                nearbyChannels.add(calculateChannel(scanResult.frequency))
            return nearbyChannels
        }
}