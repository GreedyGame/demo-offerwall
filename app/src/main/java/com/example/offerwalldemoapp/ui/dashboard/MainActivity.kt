package com.example.offerwalldemoapp.ui.dashboard

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.offerwalldemoapp.R
import com.example.offerwalldemoapp.databinding.ActivityMainBinding
import com.example.offerwalldemoapp.ui.contact.ContactFragment
import com.example.offerwalldemoapp.ui.docs.DocsFragment
import com.example.offerwalldemoapp.ui.home.HomeFragment

class MainActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityMainBinding
    private val mViewModel by viewModels<MainViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        setupObservers()
        setupDashboard()
    }

    private fun setupObservers() {
        with(mViewModel) {
            openHomeScreen.observe(this@MainActivity) {
                with(mBinding) {
                    bottomNavView.selectedItemId = R.id.menu_home
                }
            }
        }
    }

    private fun setupDashboard() {
        with(mBinding) {
            replaceFragment(HomeFragment.newInstance())
            bottomNavView.setOnItemSelectedListener {
                when (it.itemId) {
                    R.id.menu_home -> {
                        replaceFragment(HomeFragment.newInstance())
                        return@setOnItemSelectedListener true
                    }

                    R.id.menu_docs -> {
                        replaceFragment(DocsFragment.newInstance())
                        return@setOnItemSelectedListener true
                    }

                    R.id.menu_contact -> {
                        replaceFragment(ContactFragment.newInstance())
                        return@setOnItemSelectedListener true
                    }
                }
                return@setOnItemSelectedListener false
            }
            bottomNavView.setOnItemReselectedListener {
            }
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(mBinding.fcvMain.id, fragment).commit()
    }
}