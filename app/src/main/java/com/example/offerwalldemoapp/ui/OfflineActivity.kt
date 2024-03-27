package com.example.offerwalldemoapp.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.offerwalldemoapp.databinding.ActivityOfflineBinding
import com.example.offerwalldemoapp.ui.dashboard.MainActivity
import com.example.sharedlibs.deviceHasInternet
import com.example.sharedlibs.showToast

class OfflineActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityOfflineBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityOfflineBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        setupListeners()
    }

    private fun setupListeners() {
        with(mBinding) {
            btnRetry.setOnClickListener {
                if (deviceHasInternet()) {
                    startActivity(Intent(this@OfflineActivity, MainActivity::class.java))
                    finish()
                    return@setOnClickListener
                }
                showToast("Internet not detected. Please try again.")
            }
        }
    }
}