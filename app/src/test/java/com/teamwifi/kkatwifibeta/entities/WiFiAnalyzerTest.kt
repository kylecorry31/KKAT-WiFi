package com.teamwifi.kkatwifibeta.entities

import org.junit.Assert.*
import org.junit.Test

class WiFiAnalyzerTest {
    @Test
    fun testRSSIQuality(){
        assertEquals(SignalQuality.EXCELLENT, WiFiAnalyzer.rateQuality(-30))
        assertEquals(SignalQuality.EXCELLENT, WiFiAnalyzer.rateQuality(-50))
        assertEquals(SignalQuality.GOOD, WiFiAnalyzer.rateQuality(-51))
        assertEquals(SignalQuality.GOOD, WiFiAnalyzer.rateQuality(-67))
        assertEquals(SignalQuality.OK, WiFiAnalyzer.rateQuality(-68))
        assertEquals(SignalQuality.OK, WiFiAnalyzer.rateQuality(-70))
        assertEquals(SignalQuality.WEAK, WiFiAnalyzer.rateQuality(-71))
        assertEquals(SignalQuality.WEAK, WiFiAnalyzer.rateQuality(-100))
    }
}