package com.example.iapgame.racingcar_game.domain.repo

import com.example.iapgame.racingcar_game.ui.shop.CarInfo
import kotlinx.coroutines.flow.Flow

interface ShopCarsRepository {
    suspend fun updateCarOwnedState(carInfo: CarInfo, isOwned: Boolean)

    fun isCarOwned(carInfo: CarInfo): Flow<Boolean>
}