package com.example.offerwalldemoapp.ui.home

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
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
        activity?.onBackPressedDispatcher?.addCallback {
            activity?.finish()
        }
        setupListeners()
    }

    private fun setupListeners() {
        with(mBinding) {
            cardGameDemo.setOnClickListener {
                appCoordinator.showIapGameDemoApp(requireActivity())
            }
            cardIapDemo.setOnClickListener {
                appCoordinator.showIapDemoApp(requireActivity())
            }
            btnViewDocs.setOnClickListener {
                openUrl("https://pubscale.gitbook.io/offerwall-sdk")
            }
            btnContactUs.setOnClickListener {
                sendEmail(recipientEmail = "hello@pubscale.com")
            }
        }
    }

    private fun openUrl(url: String) {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(browserIntent)
    }

    private fun sendEmail(recipientEmail: String) {
        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:")
            putExtra(Intent.EXTRA_EMAIL, arrayOf(recipientEmail))
        }
        if (intent.resolveActivity(requireActivity().packageManager) != null) {
            startActivity(Intent.createChooser(intent, "Send email..."))
        }
    }
}