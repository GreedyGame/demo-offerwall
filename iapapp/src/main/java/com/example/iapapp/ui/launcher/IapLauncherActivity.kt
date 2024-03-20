package com.example.iapapp.ui.launcher

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.iapapp.databinding.ActivityIapLauncherBinding
import com.example.iapapp.di.parentModule
import com.example.iapapp.ui.home.HomeActivity
import com.pubscale.sdkone.offerwall.OfferWall
import com.pubscale.sdkone.offerwall.OfferWallConfig
import com.pubscale.sdkone.offerwall.models.OfferWallInitListener
import com.pubscale.sdkone.offerwall.models.errors.InitError
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.core.context.loadKoinModules

class IapLauncherActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityIapLauncherBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityIapLauncherBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        loadKoinModules(parentModule)
        CoroutineScope(Dispatchers.Main).launch {
//            delay(2000)
            startActivity(Intent(this@IapLauncherActivity, HomeActivity::class.java))
            finish()
        }
    }
}