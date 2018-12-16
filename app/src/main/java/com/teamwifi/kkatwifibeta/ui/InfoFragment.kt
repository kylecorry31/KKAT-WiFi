package com.teamwifi.kkatwifibeta.ui

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.teamwifi.kkatwifibeta.R
import com.teamwifi.kkatwifibeta.entities.WiFiNetwork
import kotlinx.android.synthetic.main.info_fragment.*
import java.util.*
import kotlin.concurrent.timerTask

class InfoFragment: Fragment() {

    private lateinit var currentNetwork: WiFiNetwork
//    lateinit var noConnectionBar: Snackbar
    private var timer = Timer()
    private val delay: Long = 500
    private var cancelled = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View =  inflater.inflate(R.layout.info_fragment, container, false)


        currentNetwork = WiFiNetwork(context!!)
//        noConnectionBar = Snackbar.make(coordinator, "Not connected to a WiFi network", Snackbar.LENGTH_INDEFINITE)

        return view
    }

    override fun onResume() {
        super.onResume()
//        if (!currentNetwork.isConnected && !noConnectionBar.isShown)
//            noConnectionBar.show()
        timer = Timer()
        timer.scheduleAtFixedRate(timerTask {
            Handler(context!!.mainLooper).post { updateUI() }
        }, 0, delay)
        cancelled = false
    }


    override fun onPause() {
        cancelled = true
        timer.cancel()
        super.onPause()
    }

    private fun updateUI(){
        if (cancelled){
            return
        }
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
        rssi.text = currentNetwork.rssi.toString()
        freq.text = (currentNetwork.frequency / 100 / 10.0).toString()
        channel.text = currentNetwork.channel.toString()
        desc.text =  WiFiNetwork.describeRSSIQuality(currentNetwork.rssi)
        val color: Int
        color = if (signalStrength >= 75)
            Color.rgb(245 - (signalStrength - 75) * 9, (241 + (signalStrength - 75) * 0.16).toInt(), 12)
        else
            Color.rgb((219 + signalStrength * 0.35).toInt(), (59 + signalStrength * 2.43).toInt(), (59 - signalStrength * 0.63).toInt())
        bar.setBackgroundColor(color)
    }

}
