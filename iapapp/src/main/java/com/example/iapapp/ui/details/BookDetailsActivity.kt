package com.example.iapapp.ui.details

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import com.example.iapapp.R
import com.example.iapapp.data.BookModel
import com.example.iapapp.databinding.ActivityBookDetailsBinding
import com.example.iapapp.ui.get_more_coins.GetMoreCoinsBottomSheet
import com.example.iapapp.ui.store.StoreActivity
import com.example.iapapp.utils.AppPreferences
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.concurrent.TimeUnit

class BookDetailsActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityBookDetailsBinding
    private val mViewModel by viewModel<BookDetailsViewModel>()
    private lateinit var mMediaPlayer: MediaPlayer
    private val mAppPreferences by inject<AppPreferences>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityBookDetailsBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        setupListeners()
        setupObservers()
        mViewModel.handleIntent(intent)
    }

    override fun onStart() {
        super.onStart()
        updateWallBalanceUi()
    }

    override fun onPause() {
        super.onPause()
        mViewModel.pausePlayer()
    }

    private fun setupListeners() {
        with(mBinding) {
            btnBack.setOnClickListener {
                onBackPressedDispatcher.onBackPressed()
            }
            btnPlayPause.setOnClickListener {
                mViewModel.playPauseButtonClicked()
            }
            btnRewind.setOnClickListener {
                mViewModel.updateCurrentTime(mMediaPlayer.currentPosition - 10_000)
            }
            btnFastForward.setOnClickListener {
                mViewModel.updateCurrentTime(mMediaPlayer.currentPosition + 10_000)
            }
            seekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(
                    seekBar: SeekBar?, progress: Int, fromUser: Boolean
                ) {
                    if (!fromUser) {
                        return
                    }
                    if (progress >= 10_000 && !mViewModel.isBookUnlocked()) {
                        mViewModel.pausePlayer()
                        showGetMoreCoinsDialog()
                        return
                    }
                    mViewModel.updateCurrentTime(progress)
                }

                override fun onStartTrackingTouch(seekBar: SeekBar?) {
                }

                override fun onStopTrackingTouch(seekBar: SeekBar?) {
                }
            })
            btnWallet.setOnClickListener {
                startActivity(Intent(this@BookDetailsActivity, StoreActivity::class.java))
            }
        }
    }

    private fun setupObservers() {
        with(mViewModel) {
            bookModel.observe(this@BookDetailsActivity) {
                updateBookInfo(it)
            }
            playerState.observe(this@BookDetailsActivity) {
                when (it) {
                    PlayerState.Idle -> {
                        createMediaPlayer()
                        disablePlayerSideButtons(true)
                    }

                    PlayerState.Playing -> {
                        playMediaPlayer()
                    }

                    PlayerState.Paused -> {
                        with(mBinding) {
                            btnPlayPause.setImageResource(R.drawable.ic_play)
                        }
                        mMediaPlayer.pause()
                        disablePlayerSideButtons(false)
                    }

                    PlayerState.Finished -> {
                        with(mBinding) {
                            btnPlayPause.setImageResource(R.drawable.ic_play)
                            updateCurrentTimeUi(mMediaPlayer.duration)
                            updateEndTimeUi(mMediaPlayer.duration)
                            disablePlayerSideButtons(true)
                        }
                    }

                    PlayerState.Restart -> {
                        mMediaPlayer.reset()
                        createMediaPlayer()
                        playMediaPlayer()
                    }
                }
            }
            progressWatcherRunning.observe(this@BookDetailsActivity) {
                updateCurrentTimeUi(mMediaPlayer.currentPosition)
            }
            updateCurrentTime.observe(this@BookDetailsActivity) {
                updateCurrentTimeUi(it)
                mMediaPlayer.seekTo(it)
            }
        }
    }

    private fun showGetMoreCoinsDialog() {
        val dialog = GetMoreCoinsBottomSheet.newInstance {
            updateWallBalanceUi()
            mViewModel.resumePlayer()
            mViewModel.markBookAsUnlocked()
        }
        dialog.show(supportFragmentManager, null)
    }

    private fun updateBookInfo(model: BookModel) {
        with(mBinding) {
            imgBookCover.setImageResource(model.bookCoverImage)
            tvRating.text = model.rating.toString()
            tvLanguage.text = model.language
            tvDuration.text = "${model.durationInMin} Minute"
            tvTitle.text = model.bookName
            tvAuthor.text = model.bookAuthor
        }
    }

    private fun createMediaPlayer() {
        mMediaPlayer = MediaPlayer.create(this, R.raw.sample_audio)
        with(mBinding) {
            btnPlayPause.setImageResource(R.drawable.ic_play)
            updateCurrentTimeUi(mMediaPlayer.currentPosition)
            updateEndTimeUi(mMediaPlayer.duration)
        }
    }

    private fun playMediaPlayer() {
        disablePlayerSideButtons(false)
        mMediaPlayer.setOnCompletionListener {
            mViewModel.audioFinishedPlaying()
        }
        mMediaPlayer.start()
        with(mBinding) {
            btnPlayPause.setImageResource(R.drawable.ic_pause)
        }
        mViewModel.watchProgress()
    }

    private fun updateCurrentTimeUi(currentPosition: Int) {
        with(mBinding) {
            tvCurrentTime.text = converterMillisToTime(currentPosition.toLong())
            seekbar.progress = currentPosition
        }
    }

    private fun updateEndTimeUi(duration: Int) {
        with(mBinding) {
            tvEndTime.text = converterMillisToTime(duration.toLong())
            seekbar.max = duration
        }
    }

    private fun disablePlayerSideButtons(isDisable: Boolean) {
        with(mBinding) {
            if (isDisable) {
                btnRewind.alpha = 0.6F
                btnFastForward.alpha = 0.6F

                btnRewind.isClickable = false
                btnFastForward.isClickable = false

                btnRewind.isFocusable = false
                btnFastForward.isFocusable = false
                return@with
            }
            btnRewind.alpha = 1F
            btnFastForward.alpha = 1F

            btnRewind.isClickable = true
            btnFastForward.isClickable = true

            btnRewind.isFocusable = true
            btnFastForward.isFocusable = true
        }
    }

    private fun updateWallBalanceUi() {
        with(mBinding) {
            tvWalletBalance.text = mAppPreferences.currentBalance.toString()
        }
    }

    private fun converterMillisToTime(millis: Long): String = String.format(
        "%02d:%02d", TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(
            TimeUnit.MILLISECONDS.toHours(millis)
        ), TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(
            TimeUnit.MILLISECONDS.toMinutes(millis)
        )
    )
}