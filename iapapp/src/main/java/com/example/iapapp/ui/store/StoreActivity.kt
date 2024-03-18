package com.example.iapapp.ui.store

import android.media.MediaPlayer
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.iapapp.R
import com.example.iapapp.databinding.ActivityStoreBinding
import com.example.iapapp.utils.AppPreferences
import org.koin.android.ext.android.inject

class StoreActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityStoreBinding
    private val mAppPreferences by inject<AppPreferences>()

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
            btnBuyFreeCoins.setOnClickListener {}
            tvResetWalletBalance.setOnClickListener {
                updateWalletBalance(-mAppPreferences.currentBalance)
            }
        }
    }

    private fun updateWalletBalance(credits: Int) {
        val mediaPlayer = MediaPlayer.create(this, R.raw.ka_ching)
        mediaPlayer.start()
        mAppPreferences.currentBalance += credits
        updateWalletBalanceUi()
    }

    private fun updateWalletBalanceUi() {
        with(mBinding) {
            tvWalletBalance.text = mAppPreferences.currentBalance.toString()
        }
    }
}