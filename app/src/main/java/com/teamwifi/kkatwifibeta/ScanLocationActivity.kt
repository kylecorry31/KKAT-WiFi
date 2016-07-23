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

/**
 * Created by Kyle on 7/22/2016.
 */

class ScanLocationActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_analysis_user_location)
        val currentNetwork = WiFiNetwork(this)
        val continueButton = findViewById(R.id.continue_button) as Button
        val doneButton = findViewById(R.id.noMoreButton) as Button
        val locationNameEdit = findViewById(R.id.locationText) as EditText

        continueButton.setOnClickListener(View.OnClickListener {
            if (locationNameEdit.text.isBlank()) {
                locationNameEdit.error = "Must name location!"
            } else {
                val location = ScannedLocation(locationNameEdit.text.toString())
                for (i in 0..9)
                    location.addReading(currentNetwork.rssi)
                AnalyzedLocations.add(location)
                locationNameEdit.text.clear()
                Toast.makeText(applicationContext, "Location Analyzed", Toast.LENGTH_SHORT).show()
            }
        })

        doneButton.setOnClickListener({
            startActivity(Intent(applicationContext, ResultActivity::class.java))
        })
    }

    override fun onPause() {
        super.onPause()
        finish()
    }
}