package com.example.iapapp.ui.store

import android.media.MediaPlayer
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.iapapp.R
import com.example.iapapp.databinding.ActivityStoreBinding
import com.example.iapapp.utils.AppPreferences
import com.pubscale.sdkone.offerwall.OfferWall
import com.pubscale.sdkone.offerwall.models.OfferWallListener
import com.pubscale.sdkone.offerwall.models.Reward
import org.koin.android.ext.android.inject

class StoreActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityStoreBinding
    private val mAppPreferences by inject<AppPreferences>()
    private val offerWallListener = object : OfferWallListener {

        override fun onOfferWallShowed() {
        }

        override fun onOfferWallClosed() {
        }

        override fun onRewardClaimed(reward: Reward) {
            updateWalletBalance(reward.amount.toInt())
        }

        override fun onFailed(message: String) {
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityStoreBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        setupListeners()
    }

    override fun onStart() {
        super.onStart()
        updateWalletBalanceUi()
    }

    private fun setupListeners() {
        with(mBinding) {
            btnBack.setOnClickListener {
                onBackPressedDispatcher.onBackPressed()
            }
            btnBuy200Coins.setOnClickListener {
                updateWalletBalance(200)
            }
            btnBuy400Coins.setOnClickListener {
                updateWalletBalance(400)
            }
            btnBuy800Coins.setOnClickListener {
                updateWalletBalance(800)
            }
            btnBuy1500Coins.setOnClickListener {
                updateWalletBalance(1500)
            }
            btnBuyFreeCoins.setOnClickListener {
                OfferWall.launch(this@StoreActivity, offerWallListener)
            }
            tvResetWalletBalance.setOnClickListener {
                updateWalletBalance(-mAppPreferences.currentBalance)
            }
        }
    }

    private fun updateWalletBalance(credits: Int) {
        val mediaPlayer = MediaPlayer.create(this, R.raw.ka_ching)
        mediaPlayer.start()
        mAppPreferences.currentBalance += credits
        mAppPreferences.isBook1Unlocked = false
        mAppPreferences.isBook2Unlocked = false
        mAppPreferences.isBook3Unlocked = false
        mAppPreferences.isBook4Unlocked = false
        updateWalletBalanceUi()
    }

    private fun updateWalletBalanceUi() {
        with(mBinding) {
            tvWalletBalance.text = mAppPreferences.currentBalance.toString()
        }
    }
}