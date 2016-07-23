package com.teamwifi.kkatwifibeta
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.net.ConnectivityManager
import android.net.wifi.WifiManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.CoordinatorLayout
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.view.Menu
import android.view.MenuItem
import android.widget.RelativeLayout
import android.widget.TextView
import com.teamwifi.kkatwifibeta.util.Settings
import com.teamwifi.kkatwifibeta.util.WiFiNetwork


class MainActivity : AppCompatActivity() {
    var currentNetwork: WiFiNetwork? = null
    var bottomSheet: RelativeLayout? = null
    var scanButton: FloatingActionButton? = null
    var connectivityChangedReceiver: BroadcastReceiver? = null
    var noConnectionBar: Snackbar? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_main)
        window.setBackgroundDrawable(null)
        if (Settings.isFirst(applicationContext)) {
            startActivity(Intent(applicationContext, TutorialActivity::class.java))
        }
        currentNetwork = WiFiNetwork(this)
        val ssidText = findViewById(R.id.ssid) as TextView
        val signalStrengthText = findViewById(R.id.strength) as TextView
        val descriptionText = findViewById(R.id.desc) as TextView
        val freqText = findViewById(R.id.freq) as TextView
        val rssiText = findViewById(R.id.rssi) as TextView
        val channelText = findViewById(R.id.channel) as TextView
        val mainLayout = findViewById(R.id.main) as RelativeLayout
        val coordinatorLayout = findViewById(R.id.coordinator) as CoordinatorLayout
        noConnectionBar = Snackbar.make(coordinatorLayout!!, "Not connected to a WiFi network", Snackbar.LENGTH_INDEFINITE)
        scanButton = findViewById(R.id.scanFab) as FloatingActionButton
        bottomSheet = findViewById(R.id.bottomSheet) as RelativeLayout
        scanButton!!.setOnClickListener({
            startActivity(Intent(applicationContext, BaseReadingActivity::class.java))
        })

        connectivityChangedReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                val network = currentNetwork!!
                if (!network.isConnected) {
                    if (!noConnectionBar!!.isShown) {
                        noConnectionBar!!.show()
                    }
                    return
                } else if (noConnectionBar!!.isShown) {
                    noConnectionBar!!.dismiss()
                }
                ssidText.text = network.ssid
                val signalStrength = network.signalStrength
                signalStrengthText.text = signalStrength.toString()
                rssiText.text = "${network.rssi} dBm"
                freqText.text = "${network.frequency / 100 / 10.0} GHz"
                channelText.text = "${network.channel}"
                if (signalStrength <= 20)
                    descriptionText.text = "Deadzone detected"
                else if (signalStrength <= 50)
                    descriptionText.text = "Weak signal detected"
                else
                    descriptionText.text = "Signal is fine"
                val color: Int
                if (signalStrength >= 75)
                    color = Color.rgb(245 - (signalStrength - 75) * 9, (241 + (signalStrength - 75) * 0.16).toInt(), 12)
                else
                    color = Color.rgb((219 + signalStrength * 0.35).toInt(), (59 + signalStrength * 2.43).toInt(), (59 - signalStrength * 0.63).toInt())
                mainLayout.setBackgroundColor(color)
            }
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val i: Intent
        if (item != null) {
            when (item.itemId) {
                R.id.action_learn -> {
                    i = Intent(this, LearnActivity::class.java)
                    startActivity(i)
                    return true
                }
                R.id.action_tutorial -> {
                    i = Intent(this, TutorialActivity::class.java)
                    startActivity(i)
                    return true
                }
            }
        }
        return false
    }

    override fun onResume() {
        super.onResume()
        if (!currentNetwork!!.isConnected && !noConnectionBar!!.isShown)
            noConnectionBar!!.show()
        val intentFilter = IntentFilter()
        intentFilter.addAction(WifiManager.RSSI_CHANGED_ACTION)
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION)
        registerReceiver(connectivityChangedReceiver, intentFilter)
    }

    override fun onPause() {
        unregisterReceiver(connectivityChangedReceiver)
        super.onPause()
    }
}
