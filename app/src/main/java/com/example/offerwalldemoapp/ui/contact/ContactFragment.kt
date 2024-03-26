package com.example.offerwalldemoapp.ui.contact

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.offerwalldemoapp.databinding.FragmentContactsBinding
import com.example.offerwalldemoapp.ui.dashboard.MainViewModel

class ContactFragment : Fragment() {
    private lateinit var mBinding: FragmentContactsBinding
    private val mActivityViewModel by activityViewModels<MainViewModel>()

    companion object {
        fun newInstance() = ContactFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentContactsBinding.inflate(layoutInflater)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.onBackPressedDispatcher?.addCallback {
            mActivityViewModel.openHomeScreen()
        }
    }
}