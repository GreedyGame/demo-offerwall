package com.example.offerwalldemoapp.ui.dashboard

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    private val _openHomeScreen = MutableLiveData<Unit>()
    val openHomeScreen: MutableLiveData<Unit>
        get() = _openHomeScreen

    fun openHomeScreen() {
        _openHomeScreen.postValue(Unit)
    }
}