package com.example.offerwalldemoapp.ui.dashboard

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.offerwalldemoapp.databinding.ActivityMainBinding
import com.example.offerwalldemoapp.ui.home.HomeFragment

class MainActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        setupDashboard()
    }

    private fun setupDashboard() {
        replaceFragment(HomeFragment.newInstance())
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(mBinding.fcvMain.id, fragment).commit()
    }
}