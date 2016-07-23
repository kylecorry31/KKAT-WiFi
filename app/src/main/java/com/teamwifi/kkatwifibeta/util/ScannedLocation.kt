package com.teamwifi.kkatwifibeta.util

/**
 * Created by Kyle on 7/21/2016.
 */

class ScannedLocation(val name: String) {
    private val readings: MutableList<Int> = mutableListOf()

    fun addReading(rssi: Int) = readings.add(rssi)

    val averageReading: Int
        get() = readings.average().toInt()
}