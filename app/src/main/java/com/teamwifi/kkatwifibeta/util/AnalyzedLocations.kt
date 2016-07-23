package com.teamwifi.kkatwifibeta.util
/**
 * Created by Kyle on 7/21/2016.
 */

class AnalyzedLocations() {
    companion object {
        val ROUTER = "ROUTER"
        val scannedLocations: MutableList<ScannedLocation> = mutableListOf()
        fun add(location: ScannedLocation) = scannedLocations.add(location)
        fun clear() = scannedLocations.clear()
        fun getLocation(name: String): ScannedLocation? = scannedLocations.find { it.name.equals(name) }
        fun removeLocation(name: String): Boolean {
            val location: ScannedLocation? = getLocation(name)
            if (location != null) {
                scannedLocations.remove(location)
                return true
            } else {
                return false
            }
        }
        fun getRouter(): ScannedLocation? = getLocation(ROUTER)
    }
}