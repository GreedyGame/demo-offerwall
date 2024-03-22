package com.example.iapgame.racingcar_game.data.repo

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import com.example.iapgame.racingcar_game.data.source.coinsDataStore
import com.example.iapgame.racingcar_game.domain.repo.ShopCarsRepository
import com.example.iapgame.racingcar_game.ui.shop.CarInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ShopCarsRepositoryImpl(val context: Context) : ShopCarsRepository {

    companion object {
        val SHOP_CARS_DATASTORE_KEY = intPreferencesKey("shop_cars_datastore_key")
    }

    override suspend fun updateCarOwnedState(carInfo: CarInfo, isOwned: Boolean) {
        context.coinsDataStore.edit {
            it[booleanPreferencesKey(carInfo.carId)] = isOwned
        }
    }

    override fun isCarOwned(carInfo: CarInfo): Flow<Boolean> {
        return context.coinsDataStore.data.map {
            it[booleanPreferencesKey(carInfo.carId)] ?: false
        }
    }
}