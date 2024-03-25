package com.example.iapapp.ui.get_more_coins

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.iapapp.databinding.BottomSheetGetMoreCoinsBinding
import com.example.iapapp.ui.store.StoreActivity
import com.example.iapapp.utils.AppPreferences
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.koin.android.ext.android.inject

class GetMoreCoinsBottomSheet(private val onBookPurchased: () -> Unit) :
    BottomSheetDialogFragment() {
    private lateinit var mBinding: BottomSheetGetMoreCoinsBinding
    private val mAppPreferences by inject<AppPreferences>()

    companion object {
        fun newInstance(onBookPurchased: () -> Unit) = GetMoreCoinsBottomSheet(onBookPurchased)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        mBinding = BottomSheetGetMoreCoinsBinding.inflate(layoutInflater)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isCancelable = false
        setupListeners()
    }

    override fun onStart() {
        super.onStart()
        with(mBinding) {
            val unlockCost = 100
            tvWalletBalance.text = mAppPreferences.currentBalance.toString()
            tvUnlockCost.text = unlockCost.toString()

            if (mAppPreferences.currentBalance >= unlockCost) {
                btnGetMoreCoins.text = "Unlock Now"
                btnGetMoreCoins.setOnClickListener {
                    mAppPreferences.currentBalance -= unlockCost
                    onBookPurchased.invoke()
                    dismiss()
                }
            } else {
                btnGetMoreCoins.text = "Get More Coins"
                btnGetMoreCoins.setOnClickListener {
                    startActivity(Intent(requireActivity(), StoreActivity::class.java))
                }
            }
        }
    }

    private fun setupListeners() {
        with(mBinding) {
            btnMaybeLater.setOnClickListener {
                requireActivity().onBackPressedDispatcher.onBackPressed()
            }
        }
    }
}