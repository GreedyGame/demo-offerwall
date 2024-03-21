package com.example.navigation

import android.app.Activity
import android.content.Intent
import com.example.iapapp.ui.launcher.IapLauncherActivity
import com.example.iapgame.launcher.GameDemoLauncherActivity

class AppCoordinator : AppNavigator {
    override fun showIapDemoApp(activity: Activity) {
        activity.startActivity(Intent(activity, IapLauncherActivity::class.java))
    }

    override fun showIapGameDemoApp(activity: Activity) {
        activity.startActivity(Intent(activity, GameDemoLauncherActivity::class.java))
    }
}