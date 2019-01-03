package com.teamwifi.kkatwifibeta.entities

import android.content.Context
import android.net.wifi.WifiManager
import android.os.Build


class WiFiNetwork(context: Context) {
    private val mWiFiManager = context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager

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