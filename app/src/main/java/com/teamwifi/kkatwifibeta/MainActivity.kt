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
import android.support.design.widget.Snackbar
import android.view.Menu
import android.view.MenuItem
import com.teamwifi.kkatwifibeta.util.Settings
import com.teamwifi.kkatwifibeta.util.WiFiNetwork
import kotlinx.android.synthetic.main.layout_main.*


class MainActivity : AppCompatActivity() {
    lateinit var currentNetwork: WiFiNetwork
    private lateinit var connectivityChangedReceiver: BroadcastReceiver
    lateinit var noConnectionBar: Snackbar
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_main)
        window.setBackgroundDrawable(null)
        if (Settings.isFirst(applicationContext)) {
            startActivity(Intent(applicationContext, TutorialActivity::class.java))
        }
        currentNetwork = WiFiNetwork(this)
        noConnectionBar = Snackbar.make(coordinator, "Not connected to a WiFi network", Snackbar.LENGTH_INDEFINITE)
        scanFab.setOnClickListener {
            startActivity(Intent(applicationContext, BaseReadingActivity::class.java))
        }

        connectivityChangedReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                if (!currentNetwork.isConnected) {
                    if (!noConnectionBar.isShown) {
                        noConnectionBar.show()
                    }
                    return
                } else if (noConnectionBar.isShown) {
                    noConnectionBar.dismiss()
                }
                ssid.text = currentNetwork.ssid
                val signalStrength = currentNetwork.signalStrength
                strength.text = signalStrength.toString()
                rssi.text = "${currentNetwork.rssi} dBm"
                freq.text = "${currentNetwork.frequency / 100 / 10.0} GHz"
                channel.text = "${currentNetwork.channel}"
                if (signalStrength <= 20)
                    desc.text = "Deadzone detected"
                else if (signalStrength <= 50)
                    desc.text = "Weak signal detected"
                else
                    desc.text = "Signal is fine"
                val color: Int
                if (signalStrength >= 75)
                    color = Color.rgb(245 - (signalStrength - 75) * 9, (241 + (signalStrength - 75) * 0.16).toInt(), 12)
                else
                    color = Color.rgb((219 + signalStrength * 0.35).toInt(), (59 + signalStrength * 2.43).toInt(), (59 - signalStrength * 0.63).toInt())
                main.setBackgroundColor(color)
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
        if (!currentNetwork.isConnected && !noConnectionBar.isShown)
            noConnectionBar.show()
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
