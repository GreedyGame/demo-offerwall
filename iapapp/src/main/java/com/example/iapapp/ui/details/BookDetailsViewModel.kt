package com.example.iapapp.ui.details

import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
        _bookModel.postValue(intent?.getParcelableExtra<BookModel>(IntentConstants.BOOKS_MODEL))
        _playerState.postValue(PlayerState.Idle)
    }

    fun playPauseButtonClicked() {
        when (playerState.value!!) {
            PlayerState.Idle, PlayerState.Paused -> {
                _playerState.postValue(PlayerState.Playing)
            }

            PlayerState.Playing, PlayerState.Restart -> {
                _playerState.postValue(PlayerState.Paused)
            }

            PlayerState.Finished -> {
                _playerState.postValue(PlayerState.Restart)
            }
        }
    }

    fun watchProgress() {
        viewModelScope.launch {
            when (playerState.value!!) {
                PlayerState.Playing, PlayerState.Restart -> {
                    _progressWatcherRunning.postValue(Unit)
                    delay(1000)
                    watchProgress()
                }

                PlayerState.Idle, PlayerState.Paused, PlayerState.Finished -> {
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
            PlayerState.Idle, PlayerState.Paused -> {
                _playerState.postValue(PlayerState.Playing)
            }

            PlayerState.Playing, PlayerState.Restart -> {
                return
            }

            PlayerState.Finished -> {
                _playerState.postValue(PlayerState.Restart)
            }
        }
    }

    fun pausePlayer() {
        when (playerState.value!!) {
            PlayerState.Idle, PlayerState.Paused, PlayerState.Finished -> {
                return
            }

            PlayerState.Playing, PlayerState.Restart -> {
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
        when (bookModel.value?.bookName) {
            "Moby Dick" -> {
                appPreferences.isBook1Unlocked = true
            }

            "Authority" -> {
                appPreferences.isBook2Unlocked = true
            }

            "You were never really here" -> {
                appPreferences.isBook3Unlocked = true
            }

            "1000 Black Umbrellas" -> {
                appPreferences.isBook4Unlocked = true
            }
        }
    }

    fun isBookUnlocked(): Boolean {
        return when (bookModel.value?.bookName) {
            "Moby Dick" -> {
                appPreferences.isBook1Unlocked
            }

            "Authority" -> {
                appPreferences.isBook2Unlocked
            }

            "You were never really here" -> {
                appPreferences.isBook3Unlocked
            }

            "1000 Black Umbrellas" -> {
                appPreferences.isBook4Unlocked
            }

            else -> {
                false
            }
        }
    }
}

sealed class PlayerState {
    data object Idle : PlayerState()
    data object Playing : PlayerState()
    data object Paused : PlayerState()
    data object Finished : PlayerState()
    data object Restart : PlayerState()
}