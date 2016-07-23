package com.teamwifi.kkatwifibeta.util

import android.content.SharedPreferences

/**
 * Created by Kyle on 7/21/2016.
 */
inline fun SharedPreferences.edit(func: SharedPreferences.Editor.() -> Unit) {
    val editor = edit()
    editor.func()
    editor.apply()
}