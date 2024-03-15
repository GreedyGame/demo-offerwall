package com.example.navigation

import android.app.Activity
import android.content.Intent
import com.example.iapapp.Main2Activity

class AppCoordinator : AppNavigator {
    override fun showIapDemoApp(activity: Activity) {
        activity.startActivity(Intent(activity, Main2Activity::class.java))
    }
}