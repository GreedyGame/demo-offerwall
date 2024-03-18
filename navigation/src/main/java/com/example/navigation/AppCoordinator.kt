package com.example.navigation

import android.app.Activity
import android.content.Intent
import com.example.iapapp.ui.launcher.IapLauncherActivity

class AppCoordinator : AppNavigator {
    override fun showIapDemoApp(activity: Activity) {
        activity.startActivity(Intent(activity, IapLauncherActivity::class.java))
    }
}