package com.example.iapapp.ui.home

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.iapapp.data.BookModel
import com.example.iapapp.data.GenreModel
import com.example.iapapp.databinding.ActivityHomeBinding
import com.example.iapapp.ui.adapter.BookAdapter
import com.example.iapapp.ui.adapter.GenreAdapter
import com.example.iapapp.ui.details.BookDetailsActivity
import com.example.iapapp.ui.store.StoreActivity
import com.example.iapapp.utils.AppPreferences
import com.example.iapapp.utils.IntentConstants
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityHomeBinding
    private val mViewModel by viewModel<HomeViewModel>()
    private lateinit var mGenreAdapter: GenreAdapter
    private lateinit var mBookAdapter: BookAdapter
    private val mAppPreferences by inject<AppPreferences>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        setupListeners()
        setupObservers()
        setupGenres(mViewModel.defaultGenres)
        setupBooks(mViewModel.defaultBooks)
    }

    override fun onStart() {
        super.onStart()
        with(mBinding) {
            tvWalletBalance.text = mAppPreferences.currentBalance.toString()
        }
    }

    private fun setupListeners() {
        with(mBinding) {
            btnWallet.setOnClickListener {
                startActivity(Intent(this@HomeActivity, StoreActivity::class.java))
            }
        }
    }

    private fun setupObservers() {
        with(mViewModel) {
            genres.observe(this@HomeActivity) {
                setupGenres(it)
            }
        }
    }

    private fun setupGenres(listOfGenres: List<GenreModel>) {
        with(mBinding) {
            if (!::mGenreAdapter.isInitialized) {
                mGenreAdapter = GenreAdapter {
                    mViewModel.updateGenreSelection(it)
                }
                rvGenre.adapter = mGenreAdapter
            }
            mGenreAdapter.submitList(listOfGenres)
        }
    }

    private fun setupBooks(listOfBooks: List<BookModel>) {
        with(mBinding) {
            if (!::mBookAdapter.isInitialized) {
                mBookAdapter = BookAdapter {
                    startActivity(Intent(this@HomeActivity, BookDetailsActivity::class.java).apply {
                        putExtra(IntentConstants.BOOKS_MODEL, it)
                    })
                }
                rvBooks.adapter = mBookAdapter
            }
            mBookAdapter.submitList(listOfBooks)
        }
    }
}