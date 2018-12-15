package com.teamwifi.kkatwifibeta

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.teamwifi.kkatwifibeta.util.Settings
import kotlinx.android.synthetic.main.layout_main.*
import android.support.v4.app.Fragment


class MainActivity : AppCompatActivity() {

    
    override fun onCreate(savedInstanceState: Bundle?) {
        // TODO: Request location permission
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_main)
        if (Settings.isFirst(applicationContext)) {
            startActivity(Intent(applicationContext, TutorialActivity::class.java))
        }

        switchFragment(InfoFragment())

        bottom_navigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.action_info -> {
                    switchFragment(InfoFragment())
                }
                R.id.action_scan -> {
//                    switchFragment(null)
                }
                R.id.action_learn -> {
                    switchFragment(LearnFragment())
                }
            }
            true
        }


    }

    private fun switchFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_holder, fragment)
                .commit()
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
}
