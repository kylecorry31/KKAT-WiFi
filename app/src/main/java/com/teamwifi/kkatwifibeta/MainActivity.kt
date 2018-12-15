package com.teamwifi.kkatwifibeta

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.teamwifi.kkatwifibeta.util.Settings
import kotlinx.android.synthetic.main.layout_main.*


class MainActivity : AppCompatActivity() {

    
    override fun onCreate(savedInstanceState: Bundle?) {
        // TODO: Request location permission
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_main)
        if (Settings.isFirst(applicationContext)) {
            startActivity(Intent(applicationContext, TutorialActivity::class.java))
        }
        // TODO: Remember selected


        syncFragmentWithSelection(bottom_navigation.selectedItemId)

        bottom_navigation.setOnNavigationItemSelectedListener { item ->
            syncFragmentWithSelection(item.itemId)
            true
        }

    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        bottom_navigation.selectedItemId = savedInstanceState?.getInt("page", R.id.action_info)!!

    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putInt("page", bottom_navigation.selectedItemId)
    }

    private fun syncFragmentWithSelection(selection: Int){
        when (selection) {
            R.id.action_info -> {
                switchFragment(InfoFragment())
            }
            R.id.action_scan -> {
                    switchFragment(ScanFragment())
            }
            R.id.action_learn -> {
                switchFragment(LearnFragment())
            }
        }
    }

    private fun switchFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_holder, fragment)
                .commit()
    }

}
