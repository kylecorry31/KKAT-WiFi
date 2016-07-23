package com.teamwifi.kkatwifibeta

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.teamwifi.kkatwifibeta.util.AnalyzedLocations
import com.teamwifi.kkatwifibeta.util.ScannedLocation
import com.teamwifi.kkatwifibeta.util.WiFiNetwork
import kotlinx.android.synthetic.main.layout_analysis_router.*

/**
 * Created by Kyle on 7/22/2016.
 */

class BaseReadingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_analysis_router)
        val currentNetwork = WiFiNetwork(applicationContext)
        AnalyzedLocations.clear()
        continue_button.setOnClickListener({
            val location = ScannedLocation(AnalyzedLocations.ROUTER)
            for (i in 0..9) {
                location.addReading(currentNetwork.rssi)
            }
            AnalyzedLocations.add(location)
            startActivity(Intent(applicationContext, ScanLocationActivity::class.java))
        })
    }

    override fun onPause() {
        super.onPause()
        finish()
    }
}