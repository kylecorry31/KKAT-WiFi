package com.teamwifi.kkatwifibeta
import android.os.Bundle
import android.support.v7.app.AppCompatActivity

/**
 * Created by Kyle on 7/22/2016.
 */
class LearnActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (supportActionBar != null)
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        setContentView(R.layout.layout_learn)
    }
}