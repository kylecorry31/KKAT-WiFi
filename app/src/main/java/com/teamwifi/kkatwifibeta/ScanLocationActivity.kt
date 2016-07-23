package com.teamwifi.kkatwifibeta

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.teamwifi.kkatwifibeta.util.AnalyzedLocations
import com.teamwifi.kkatwifibeta.util.ScannedLocation
import com.teamwifi.kkatwifibeta.util.WiFiNetwork
import kotlinx.android.synthetic.main.layout_analysis_user_location.*

/**
 * Created by Kyle on 7/22/2016.
 */

class ScanLocationActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_analysis_user_location)
        val currentNetwork = WiFiNetwork(this)

        continue_button.setOnClickListener({
            if (locationText.text.isBlank()) {
                locationText.error = "Must name location!"
            } else {
                val location = ScannedLocation(locationText.text.toString())
                for (i in 0..9)
                    location.addReading(currentNetwork.rssi)
                AnalyzedLocations.add(location)
                locationText.text.clear()
                Toast.makeText(applicationContext, "Location Analyzed", Toast.LENGTH_SHORT).show()
            }
        })

        noMoreButton.setOnClickListener({
            startActivity(Intent(applicationContext, ResultActivity::class.java))
        })
    }

    override fun onPause() {
        super.onPause()
        finish()
    }
}