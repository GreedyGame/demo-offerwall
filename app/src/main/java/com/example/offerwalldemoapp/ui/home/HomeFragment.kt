package com.example.offerwalldemoapp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.offerwalldemoapp.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private lateinit var mBinding: FragmentHomeBinding

    companion object {
        fun newInstance() = HomeFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentHomeBinding.inflate(layoutInflater)
        return mBinding.root
    }
}