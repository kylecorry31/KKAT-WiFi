package com.teamwifi.kkatwifibeta
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import com.teamwifi.kkatwifibeta.util.AnalyzedLocations
import com.teamwifi.kkatwifibeta.util.ScannedLocation
import com.teamwifi.kkatwifibeta.util.WiFiNetwork

/**
 * Created by Kyle on 7/22/2016.
 */
class ResultActivity : AppCompatActivity() {
    private val badLocations: MutableList<ScannedLocation> = mutableListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_result)
        analyzeLocations()
    }

    private fun analyzeLocations() {
        val helperText = findViewById(R.id.ominn) as TextView
        val router: ScannedLocation? = AnalyzedLocations.getRouter()
        if (router != null) {
            val routerRSSI = router.averageReading
            var text: String
            for (location in AnalyzedLocations.scannedLocations) {
                if (location.name.equals(AnalyzedLocations.ROUTER)) {
                    continue
                }
                if (WiFiNetwork.isObjectInPath(location.averageReading, routerRSSI) && !WiFiNetwork.isSignalStrengthGood(location.averageReading)) {
                    badLocations.add(location)
                }
            }
            if (badLocations.isEmpty()) {
                text = "All scanned locations were good! Your router is in the best position."
            } else {
                text = "Try moving the router closer to the following locations, or if the router is already close to them, check for any metal or glass between the router and the location.\n"
                for (badLocation in badLocations) {
                    text += "\n${badLocation.name}\n"
                }
            }
            helperText.text = text
        }
    }

}