package com.example.iapapp.ui.details

import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.iapapp.R
import com.example.iapapp.data.BookModel
import com.example.iapapp.utils.AppPreferences
import com.example.iapapp.utils.IntentConstants
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class BookDetailsViewModel(private val appPreferences: AppPreferences) : ViewModel() {
    private val _bookModel = MutableLiveData<BookModel>()
    val bookModel: LiveData<BookModel>
        get() = _bookModel

    private val _playerState = MutableLiveData<PlayerState>()
    val playerState: LiveData<PlayerState>
        get() = _playerState

    private val _progressWatcherRunning = MutableLiveData<Unit>()
    val progressWatcherRunning: LiveData<Unit>
        get() = _progressWatcherRunning

    private val _updateCurrentTime = MutableLiveData<Int>()
    val updateCurrentTime: LiveData<Int>
        get() = _updateCurrentTime

    fun handleIntent(intent: Intent?) {
        val book = intent?.getParcelableExtra<BookModel>(IntentConstants.BOOKS_MODEL)
        _bookModel.postValue(book!!)
        _playerState.postValue(PlayerState.Idle(book))
    }

    fun playPauseButtonClicked() {
        when (playerState.value!!) {
            is PlayerState.Idle, PlayerState.Paused -> {
                _playerState.postValue(PlayerState.Playing)
            }

            is PlayerState.Restart, PlayerState.Playing -> {
                _playerState.postValue(PlayerState.Paused)
            }

            PlayerState.Finished -> {
                _playerState.postValue(PlayerState.Restart(bookModel.value!!))
            }
        }
    }

    fun watchProgress() {
        viewModelScope.launch {
            when (playerState.value!!) {
                PlayerState.Playing, is PlayerState.Restart -> {
                    _progressWatcherRunning.postValue(Unit)
                    delay(1000)
                    watchProgress()
                }

                is PlayerState.Idle, PlayerState.Paused, PlayerState.Finished -> {
                    return@launch
                }
            }
        }
    }

    fun audioFinishedPlaying() {
        _playerState.postValue(PlayerState.Finished)
    }

    fun resumePlayer() {
        when (playerState.value!!) {
            is PlayerState.Idle, PlayerState.Paused -> {
                _playerState.postValue(PlayerState.Playing)
            }

            PlayerState.Playing, is PlayerState.Restart -> {
                return
            }

            PlayerState.Finished -> {
                _playerState.postValue(PlayerState.Restart(bookModel.value!!))
            }
        }
    }

    fun pausePlayer() {
        when (playerState.value!!) {
            is PlayerState.Idle, PlayerState.Paused, PlayerState.Finished -> {
                return
            }

            PlayerState.Playing, is PlayerState.Restart -> {
                _playerState.postValue(PlayerState.Paused)
            }
        }
    }

    fun updateCurrentTime(progress: Int) {
        if (progress < 0) {
            _updateCurrentTime.postValue(0)
            return
        }
        _updateCurrentTime.postValue(progress)
    }

    fun markBookAsUnlocked() {
        when (bookModel.value?.bookCoverImage) {
            R.drawable.ic_book_1 -> {
                appPreferences.isBook1Unlocked = true
            }

            R.drawable.ic_book_2 -> {
                appPreferences.isBook2Unlocked = true
            }

            R.drawable.ic_book_3 -> {
                appPreferences.isBook3Unlocked = true
            }

            R.drawable.ic_book_4 -> {
                appPreferences.isBook4Unlocked = true
            }
        }
    }

    fun isBookUnlocked(): Boolean {
        return when (bookModel.value?.bookCoverImage) {
            R.drawable.ic_book_1 -> {
                appPreferences.isBook1Unlocked
            }

            R.drawable.ic_book_2 -> {
                appPreferences.isBook2Unlocked
            }

            R.drawable.ic_book_3 -> {
                appPreferences.isBook3Unlocked
            }

            R.drawable.ic_book_4 -> {
                appPreferences.isBook4Unlocked
            }

            else -> {
                false
            }
        }
    }
}

sealed class PlayerState {
    data class Idle(val bookModel: BookModel) : PlayerState()
    data object Playing : PlayerState()
    data object Paused : PlayerState()
    data object Finished : PlayerState()
    data class Restart(val bookModel: BookModel) : PlayerState()
}