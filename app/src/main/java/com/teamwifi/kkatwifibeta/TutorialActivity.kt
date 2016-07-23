package com.teamwifi.kkatwifibeta

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.view.Window
import android.widget.Button
import com.teamwifi.kkatwifibeta.tutorial.AnalysisTutorialContent
import com.teamwifi.kkatwifibeta.tutorial.DeadzoneTutorialContent
import com.teamwifi.kkatwifibeta.tutorial.MainTutorialContent
import com.teamwifi.kkatwifibeta.ui.DotsPageIndicator
import com.teamwifi.kkatwifibeta.util.Settings

/**
 * Created by Kyle on 7/22/2016.
 */
class TutorialActivity : FragmentActivity() {
    val pages = 3
    private var pager: ViewPager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.layout_tutorial2)
        window.setBackgroundDrawable(null)
        val progressDots = findViewById(R.id.progressDots) as DotsPageIndicator
        val start = findViewById(R.id.getStarted) as Button
        start.setOnClickListener({
            Settings.setFirst(applicationContext, false)
            startActivity(Intent(applicationContext, MainActivity::class.java))
            finish()
        })
        pager = findViewById(R.id.pager) as ViewPager
        val pagerAdapter = ContentPageAdapter(supportFragmentManager)
        pager!!.adapter = pagerAdapter
        progressDots.setViewPager(pager!!) // TODO: Reimplement

    }

    inner class ContentPageAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
        override fun getItem(position: Int): Fragment {
            when (position) {
                0 -> return MainTutorialContent()
                1 -> return AnalysisTutorialContent()
                2 -> return DeadzoneTutorialContent()
            }
            return MainTutorialContent()
        }

        override fun getCount(): Int {
            return pages
        }
    }
}