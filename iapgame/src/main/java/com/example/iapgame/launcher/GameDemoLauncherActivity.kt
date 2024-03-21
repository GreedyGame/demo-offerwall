package com.example.iapgame.launcher

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.iapgame.databinding.ActivityGameDemoLauncherBinding
import com.example.iapgame.di.parentModule
import com.example.iapgame.racingcar_game.ui.MainActivity
import org.koin.core.context.loadKoinModules

class GameDemoLauncherActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityGameDemoLauncherBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityGameDemoLauncherBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        loadKoinModules(parentModule)
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}