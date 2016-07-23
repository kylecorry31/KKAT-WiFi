package com.teamwifi.kkatwifibeta.tutorial

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.teamwifi.kkatwifibeta.R
import kotlinx.android.synthetic.main.layout_tutorial_content.*

/**
 * Created by kyle on 9/6/15.
 */
class MainTutorialContent : Fragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView = inflater!!.inflate(
                R.layout.layout_tutorial_content, container, false) as ViewGroup

        feature_name.text = getString(R.string.app_name)
        description.text = "Solve your WiFi issues!"
        photo.setImageResource(R.drawable.ic_splash)

        return rootView
    }
}