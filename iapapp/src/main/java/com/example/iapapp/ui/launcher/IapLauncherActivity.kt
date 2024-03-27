package com.example.iapapp.ui.launcher

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AppCompatActivity
import com.example.iapapp.R
import com.example.iapapp.databinding.ActivityIapLauncherBinding
import com.example.iapapp.di.parentModule
import com.example.iapapp.ui.home.HomeActivity
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

class IapLauncherActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityIapLauncherBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityIapLauncherBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        loadKoinModules(parentModule)
        setupOfferwall()
        CoroutineScope(Dispatchers.Main).launch {
            delay(1000)
            startActivity(Intent(this@IapLauncherActivity, HomeActivity::class.java))
            finish()
        }
    }

    fun Context.drawableToBitmap(@DrawableRes drawable: Int): Bitmap {
        return BitmapFactory.decodeResource(this.resources, drawable)
    }

    private fun setupOfferwall() {
        OfferWall.destroy()
        val offerWallConfig =
            OfferWallConfig.Builder(this, "66648544")
                .setLoaderForegroundBitmap(svgToBitmap(R.drawable.ic_pocket_audio_logo)!!)
                .setFullscreenEnabled(true).build()
        OfferWall.init(offerWallConfig, object : OfferWallInitListener {
            override fun onInitSuccess() {
            }

            override fun onInitFailed(error: InitError) {
            }
        })
    }
}