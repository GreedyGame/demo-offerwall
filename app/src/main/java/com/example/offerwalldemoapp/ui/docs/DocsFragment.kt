package com.example.offerwalldemoapp.ui.docs

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.addCallback
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.offerwalldemoapp.databinding.FragmentDocsBinding
import com.example.offerwalldemoapp.ui.dashboard.MainViewModel


class DocsFragment : Fragment() {
    private lateinit var mBinding: FragmentDocsBinding
    private val mActivityViewModel by activityViewModels<MainViewModel>()
    private val mWebViewClient = object : WebViewClient() {
        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)
            mBinding.progressBar.isVisible = false
        }

        override fun shouldOverrideUrlLoading(
            view: WebView?,
            request: WebResourceRequest?
        ): Boolean {
            val intent = Intent(Intent.ACTION_VIEW, request!!.url)
            context?.startActivity(intent)
            return true
        }
    }

    companion object {
        fun newInstance() = DocsFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentDocsBinding.inflate(layoutInflater)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(mBinding) {
            activity?.onBackPressedDispatcher?.addCallback {
                if (webView.canGoBack()) {
                    webView.goBack()
                } else {
                    mActivityViewModel.openHomeScreen()
                }
            }
            progressBar.isVisible = true
            with(webView) {
                webViewClient = mWebViewClient
                settings.javaScriptEnabled = true
                settings.domStorageEnabled = true
                loadUrl("https://pubscale.gitbook.io/offerwall-sdk")
            }
        }
    }
}