package com.example.offerwalldemoapp.ui.contact

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.offerwalldemoapp.databinding.FragmentContactsBinding

class ContactFragment : Fragment() {
    private lateinit var mBinding: FragmentContactsBinding

    companion object {
        fun newInstance() = ContactFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentContactsBinding.inflate(layoutInflater)
        return mBinding.root
    }
}