package com.example.iapgame.racingcar_game.domain.repo

import kotlinx.coroutines.flow.Flow

interface CoinsRepository {
    suspend fun saveCoins(coins: Int)

    fun getCoins(): Flow<Int>
}