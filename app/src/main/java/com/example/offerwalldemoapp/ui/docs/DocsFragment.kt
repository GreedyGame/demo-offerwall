package com.example.offerwalldemoapp.ui.docs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.example.offerwalldemoapp.databinding.FragmentDocsBinding

class DocsFragment : Fragment() {
    private lateinit var mBinding: FragmentDocsBinding
    private val mWebViewClient = object : WebViewClient() {
        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)
            mBinding.progressBar.isVisible = false
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