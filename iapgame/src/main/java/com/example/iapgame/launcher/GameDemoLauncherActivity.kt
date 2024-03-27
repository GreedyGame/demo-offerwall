package com.example.iapgame.launcher

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.iapgame.databinding.ActivityGameDemoLauncherBinding
import com.example.iapgame.di.parentModule
import com.example.iapgame.racingcar_game.ui.MainActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.core.context.loadKoinModules

class GameDemoLauncherActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityGameDemoLauncherBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityGameDemoLauncherBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        loadKoinModules(parentModule)
        CoroutineScope(Dispatchers.Main).launch {
            delay(1000)
            startActivity(Intent(this@GameDemoLauncherActivity, MainActivity::class.java))
            finish()
        }
    }
}