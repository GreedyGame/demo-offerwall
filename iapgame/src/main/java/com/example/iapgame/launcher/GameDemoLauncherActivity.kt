package com.example.iapgame.launcher

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.iapgame.R
import com.example.iapgame.databinding.ActivityGameDemoLauncherBinding
import com.example.iapgame.di.parentModule
import com.example.iapgame.racingcar_game.ui.MainActivity
import com.example.sharedlibs.svgToBitmap
import com.pubscale.sdkone.offerwall.OfferWall
import com.pubscale.sdkone.offerwall.OfferWallConfig
import com.pubscale.sdkone.offerwall.models.OfferWallInitListener
import com.pubscale.sdkone.offerwall.models.errors.InitError
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
        setupOfferwall()
        CoroutineScope(Dispatchers.Main).launch {
            delay(1000)
            startActivity(Intent(this@GameDemoLauncherActivity, MainActivity::class.java))
            finish()
        }
    }

    private fun setupOfferwall() {
        OfferWall.destroy()
        val offerWallConfig =
            OfferWallConfig.Builder(this, "test_63902223")
                .setLoaderForegroundBitmap(svgToBitmap(R.drawable.ic_racing_game_logo)!!)
                .setFullscreenEnabled(true).build()
        OfferWall.init(offerWallConfig, object : OfferWallInitListener {
            override fun onInitSuccess() {
            }

            override fun onInitFailed(error: InitError) {
            }
        })
    }
}