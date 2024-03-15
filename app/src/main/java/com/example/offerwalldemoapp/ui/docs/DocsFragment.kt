package com.example.offerwalldemoapp.ui.docs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.offerwalldemoapp.databinding.FragmentDocsBinding

class DocsFragment : Fragment() {
    private lateinit var mBinding: FragmentDocsBinding

    companion object {
        fun newInstance() = DocsFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentDocsBinding.inflate(layoutInflater)
        return mBinding.root
    }
}