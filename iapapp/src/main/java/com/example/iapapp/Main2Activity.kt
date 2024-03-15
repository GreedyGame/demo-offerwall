package com.example.iapapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.iapapp.databinding.ActivityMain2Binding

class Main2Activity : AppCompatActivity() {
    private lateinit var mBinding: ActivityMain2Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(mBinding.root)
    }
}