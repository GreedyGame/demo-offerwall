package com.example.iapgame.racingcar_game.data.repo

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import com.example.iapgame.racingcar_game.data.source.coinsDataStore
import com.example.iapgame.racingcar_game.domain.repo.CoinsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CoinsRepositoryImpl(val context: Context) : CoinsRepository {

    companion object {
        val COINS_DATASTORE_KEY = intPreferencesKey("coins_datastore_key")
    }

    override suspend fun saveCoins(coins: Int) {
        context.coinsDataStore.edit {
            it[COINS_DATASTORE_KEY] = coins
        }
    }

    override fun getCoins(): Flow<Int> {
        return context.coinsDataStore.data.map {
            it[COINS_DATASTORE_KEY] ?: 100
        }
    }
}