package com.teamwifi.kkatwifibeta.util

/**
 * Created by Kyle on 7/21/2016.
 */

object AnalyzedLocations {
    val ROUTER = "ROUTER"
    val scannedLocations: MutableList<ScannedLocation> = mutableListOf()
    fun add(location: ScannedLocation) = scannedLocations.add(location)
    fun clear() = scannedLocations.clear()
    fun getLocation(name: String): ScannedLocation? = scannedLocations.find { it.name.equals(name) }
    fun getRouter(): ScannedLocation? = getLocation(ROUTER)
}