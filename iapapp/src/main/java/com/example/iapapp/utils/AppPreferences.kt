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

    var isBook1Unlocked: Boolean
        set(value) {
            editor.putBoolean(Keys.IS_BOOK_1_UNLOCKED, value).commit()
        }
        get() {
            return pref.getBoolean(Keys.IS_BOOK_1_UNLOCKED, false)
        }

    var isBook2Unlocked: Boolean
        set(value) {
            editor.putBoolean(Keys.IS_BOOK_2_UNLOCKED, value).commit()
        }
        get() {
            return pref.getBoolean(Keys.IS_BOOK_2_UNLOCKED, false)
        }

    var isBook3Unlocked: Boolean
        set(value) {
            editor.putBoolean(Keys.IS_BOOK_3_UNLOCKED, value).commit()
        }
        get() {
            return pref.getBoolean(Keys.IS_BOOK_3_UNLOCKED, false)
        }

    var isBook4Unlocked: Boolean
        set(value) {
            editor.putBoolean(Keys.IS_BOOK_4_UNLOCKED, value).commit()
        }
        get() {
            return pref.getBoolean(Keys.IS_BOOK_4_UNLOCKED, false)
        }

    object Keys {
        const val CURRENT_BALANCE = "CURRENT_BALANCE"
        const val IS_BOOK_1_UNLOCKED = "IS_BOOK_1_UNLOCKED"
        const val IS_BOOK_2_UNLOCKED = "IS_BOOK_2_UNLOCKED"
        const val IS_BOOK_3_UNLOCKED = "IS_BOOK_3_UNLOCKED"
        const val IS_BOOK_4_UNLOCKED = "IS_BOOK_4_UNLOCKED"
    }
}