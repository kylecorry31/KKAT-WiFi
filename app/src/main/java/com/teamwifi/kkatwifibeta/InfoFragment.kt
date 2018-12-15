package com.teamwifi.kkatwifibeta

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.net.ConnectivityManager
import android.net.wifi.WifiManager
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.teamwifi.kkatwifibeta.util.WiFiNetwork
import kotlinx.android.synthetic.main.info_fragment.*

class InfoFragment: Fragment() {

    lateinit var currentNetwork: WiFiNetwork
    private lateinit var connectivityChangedReceiver: BroadcastReceiver
//    lateinit var noConnectionBar: Snackbar

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View =  inflater.inflate(R.layout.info_fragment, container, false)


        currentNetwork = WiFiNetwork(context!!)
//        noConnectionBar = Snackbar.make(coordinator, "Not connected to a WiFi network", Snackbar.LENGTH_INDEFINITE)

        connectivityChangedReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                if (!currentNetwork.isConnected) {
//                    if (!noConnectionBar.isShown) {
//                        noConnectionBar.show()
//                    }
                    return
                }
//                else if (noConnectionBar.isShown) {
//                    noConnectionBar.dismiss()
//                }
                ssid.text = currentNetwork.ssid
                val signalStrength = currentNetwork.signalStrength
                strength.text = signalStrength.toString()
                rssi2.text = currentNetwork.rssi.toString()
                freq2.text = (currentNetwork.frequency / 100 / 10.0).toString()
                channel2.text = currentNetwork.channel.toString()
                desc.text =  WiFiNetwork.describeRSSIQuality(currentNetwork.rssi)
                val color: Int
                if (signalStrength >= 75)
                    color = Color.rgb(245 - (signalStrength - 75) * 9, (241 + (signalStrength - 75) * 0.16).toInt(), 12)
                else
                    color = Color.rgb((219 + signalStrength * 0.35).toInt(), (59 + signalStrength * 2.43).toInt(), (59 - signalStrength * 0.63).toInt())
                bar.setBackgroundColor(color)
            }
        }

        return view
    }

    override fun onResume() {
        super.onResume()
//        if (!currentNetwork.isConnected && !noConnectionBar.isShown)
//            noConnectionBar.show()
        val intentFilter = IntentFilter()
        intentFilter.addAction(WifiManager.RSSI_CHANGED_ACTION)
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION)
        context!!.registerReceiver(connectivityChangedReceiver, intentFilter)
    }


    override fun onPause() {
        context!!.unregisterReceiver(connectivityChangedReceiver)
        super.onPause()
    }

}
