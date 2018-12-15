package com.teamwifi.kkatwifibeta.util

import kotlin.math.roundToInt

/**
 * Created by Kyle on 7/21/2016.
 */

class ScannedLocation(var name: String) {
    private val readings: MutableList<Int> = mutableListOf()

    fun addReading(rssi: Int) = readings.add(rssi)

    val averageReading: Int
        get() = readings.average().roundToInt()
}