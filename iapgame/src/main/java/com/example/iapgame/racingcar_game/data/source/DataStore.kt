package com.example.iapgame.racingcar_game.data.source

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore

val Context.highscoreDataStore: DataStore<Preferences> by preferencesDataStore(name = "highscore")
val Context.coinsDataStore: DataStore<Preferences> by preferencesDataStore(name = "coins")
val Context.cars: DataStore<Preferences> by preferencesDataStore(name = "cars")
