package com.example.offerwalldemoapp.ui.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.offerwalldemoapp.ui.OfflineActivity
import com.example.offerwalldemoapp.ui.dashboard.MainActivity
import com.example.sharedlibs.deviceHasInternet

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        if (deviceHasInternet()) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
            return
        }
        startActivity(Intent(this, OfflineActivity::class.java))
        finish()
    }
}