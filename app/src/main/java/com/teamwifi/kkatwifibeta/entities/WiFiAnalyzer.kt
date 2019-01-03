package com.teamwifi.kkatwifibeta.entities

class WiFiAnalyzer {
    companion object {
        /**
         * Rate the quality of the RSSI.
         *
         * @param rssi The RSSI of the network.
         * @return The signal quality of the network.
         */
        fun rateQuality(rssi: Int): SignalQuality {
            return when {
                rssi >= -50 -> SignalQuality.EXCELLENT
                rssi >= -67 -> SignalQuality.GOOD
                rssi >= -70 -> SignalQuality.OK
                else -> SignalQuality.WEAK
            }
        }

        fun describeQuality(quality: SignalQuality): String { // TODO: Does this belong here?
            return when (quality) {
                SignalQuality.EXCELLENT -> "Excellent signal detected"
                SignalQuality.GOOD -> "Good signal detected"
                SignalQuality.OK -> "Weak signal detected"
                else -> "Very bad signal detected"
            }
        }
    }
}