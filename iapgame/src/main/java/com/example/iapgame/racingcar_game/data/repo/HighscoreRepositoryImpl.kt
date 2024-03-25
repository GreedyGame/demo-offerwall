package com.example.iapgame.racingcar_game.data.repo

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import com.example.iapgame.racingcar_game.data.source.highscoreDataStore
import com.example.iapgame.racingcar_game.domain.repo.HighscoreRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class HighscoreRepositoryImpl(val context: Context) : HighscoreRepository {

    override suspend fun saveHighScore(score: Int) {
        context.highscoreDataStore.edit {
            it[HIGHSCORE_DATASTORE_KEY] = score
        }
    }

    override fun getHighScore(): Flow<Int> {
        return context.highscoreDataStore.data.map {
            it[HIGHSCORE_DATASTORE_KEY] ?: 0
        }
    }

    companion object {
        val HIGHSCORE_DATASTORE_KEY = intPreferencesKey("highscore_datastore_key")
    }

}