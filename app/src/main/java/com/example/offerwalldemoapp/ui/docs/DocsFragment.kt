package com.example.offerwalldemoapp.ui.docs

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebResourceError
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
    private val mWebViewClient by lazy {
        object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                hideLoader()
            }

            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?
            ): Boolean {
                val intent = Intent(Intent.ACTION_VIEW, request!!.url)
                context?.startActivity(intent)
                return true
            }

            override fun onReceivedError(
                view: WebView?,
                request: WebResourceRequest?,
                error: WebResourceError?
            ) {
                super.onReceivedError(view, request, error)
                hideLoader()
            }
        }
    }

    private fun hideLoader() {
        with(mBinding) {
            progressBar.isVisible = false
            swipeToRefresh.isVisible = true
            swipeToRefresh.isRefreshing = false
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
        setupWebView()
        setupListeners()
    }

    private fun setupWebView() {
        with(mBinding) {
            activity?.onBackPressedDispatcher?.addCallback {
                if (webView.canGoBack()) {
                    webView.goBack()
                } else {
                    mActivityViewModel.openHomeScreen()
                }
            }
            with(webView) {
                webViewClient = mWebViewClient
                settings.javaScriptEnabled = true
                settings.domStorageEnabled = true
                launchUrl()
            }
        }
    }

    private fun setupListeners() {
        with(mBinding) {
            swipeToRefresh.setOnRefreshListener {
                launchUrl()
            }
        }
    }

    private fun launchUrl() {
        mBinding.webView.loadUrl("https://pubscale.gitbook.io/offerwall-sdk")
    }
}