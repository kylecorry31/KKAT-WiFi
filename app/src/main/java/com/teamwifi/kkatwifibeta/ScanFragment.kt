package com.teamwifi.kkatwifibeta

import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import com.teamwifi.kkatwifibeta.entities.WiFiNetwork
import kotlinx.android.synthetic.main.scan_fragment.*
import java.util.*
import kotlin.concurrent.timerTask
import kotlin.math.roundToInt

class ScanFragment: Fragment() {

    var list = mutableListOf<Int>()
    lateinit var currentNetwork: WiFiNetwork
    var timer = Timer()
    private var cancelled = false


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.scan_fragment, container, false)
        currentNetwork = WiFiNetwork(context!!)

        return view
    }

    override fun onResume() {
        super.onResume()

        // Add

        reset.setOnClickListener {
            println(list)
            list.clear()
            analysisHeader.visibility = View.INVISIBLE
            analysisResults.text = ""
            scanProgress.progress = 0
            scanProgressText.text = "Nothing recorded yet"
            reset.visibility = View.GONE
        }

        record.setOnTouchListener { v, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    reset.visibility = View.VISIBLE
                    cancelled = false
                    timer = Timer()
                    timer.scheduleAtFixedRate(timerTask { scan() }, 0, 75)
                }
                MotionEvent.ACTION_UP -> {
                    cancelled = true
                    timer.cancel()
                }
            }
            true
        }
    }

    override fun onPause() {
        cancelled = true
        timer.cancel()
        super.onPause()
    }

    private fun scan(){
        if (cancelled){
            return
        }
        list.add(currentNetwork.rssi)
        scanProgress.progress = ((list.size / 100.0) * 100).toInt()

        if (scanProgress.progress <= 50){
            Handler(context!!.mainLooper).post {
                scanProgressText.text = "Recording..."
            }
        } else if (scanProgress.progress <= 75){
            Handler(context!!.mainLooper).post {
                scanProgressText.text = "Keep it up!"
            }
        } else if (scanProgress.progress < 100){
            Handler(context!!.mainLooper).post {
                scanProgressText.text = "Almost done!"
            }
        } else {
            Handler(context!!.mainLooper).post {
                scanProgressText.text = "Done! You can record more if you want."

                val rssi = list.average().roundToInt()
                val quality = WiFiNetwork.calculateRSSIQuality(rssi)

                var alerts = WiFiNetwork.describeRSSIQuality(rssi) + ".\n"

                if (quality <= 2){
                    alerts += "You should move your router closer to this room.\n"
                }

                analysisHeader.visibility = View.VISIBLE
                analysisResults.text = alerts
            }
        }
    }



}
