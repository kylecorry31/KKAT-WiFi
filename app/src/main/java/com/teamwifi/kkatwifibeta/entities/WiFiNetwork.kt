package com.teamwifi.kkatwifibeta.entities

import android.content.Context
import android.net.wifi.WifiManager
import android.os.Build


class WiFiNetwork(context: Context) {
    private val mWiFiManager = context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager

    companion object { // TODO: don't use these
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

    }

    val isConnected: Boolean
        get() = mWiFiManager.connectionInfo.networkId != -1

    val channel: Int
        get() {
            val freq = frequency
            if (freq == -1) return -1
            if (freq == 2484)
                return 14
            if (freq < 2484)
                return (freq - 2407) / 5
            return freq / 5 - 1000
        }

    val rssi: Int
        get() = mWiFiManager.connectionInfo.rssi

    val rssiQuality: SignalQuality
        get() {
            val quality = calculateRSSIQuality(rssi)
            return when (quality) {
                7, 6 -> SignalQuality.EXCELLENT
                5, 4 -> SignalQuality.GOOD
                3 -> SignalQuality.WEAK
                else -> SignalQuality.BAD
            }
        }

    val frequency: Int
        get() {
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                mWiFiManager.connectionInfo.frequency
            } else {
                -1
            }
        }

    val linkSpeed: Int
        get() = mWiFiManager.connectionInfo.linkSpeed

    val ssid: String
        get() = mWiFiManager.connectionInfo.ssid.replace("\"", "")

    val signalStrength: Int
        get() = WifiManager.calculateSignalLevel(rssi, 101)
}