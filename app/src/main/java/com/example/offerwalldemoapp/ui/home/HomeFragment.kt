package com.example.offerwalldemoapp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.navigation.AppCoordinator
import com.example.offerwalldemoapp.databinding.FragmentHomeBinding
import org.koin.android.ext.android.inject

class HomeFragment : Fragment() {
    private lateinit var mBinding: FragmentHomeBinding
    private val appCoordinator: AppCoordinator by inject()

    companion object {
        fun newInstance() = HomeFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentHomeBinding.inflate(layoutInflater)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListeners()
    }

    private fun setupListeners() {
        with(mBinding) {
            cardIapDemo.setOnClickListener {
                appCoordinator.showIapDemoApp(requireActivity())
            }
        }
    }
}