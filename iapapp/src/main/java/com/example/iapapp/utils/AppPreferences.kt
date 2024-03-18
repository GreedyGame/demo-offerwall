package com.example.iapapp.utils

import android.app.Application
import android.content.SharedPreferences

class AppPreferences(private val application: Application) {
    private val pref: SharedPreferences by lazy { application.getSharedPreferences("MyPref", 0) }
    private val editor by lazy { pref.edit() }

    var currentBalance: Int
        set(value) {
            editor.putInt(Keys.CURRENT_BALANCE, value).commit()
        }
        get() {
            return pref.getInt(Keys.CURRENT_BALANCE, 0)
        }

    object Keys {
        const val CURRENT_BALANCE = "CURRENT_BALANCE"
    }
}