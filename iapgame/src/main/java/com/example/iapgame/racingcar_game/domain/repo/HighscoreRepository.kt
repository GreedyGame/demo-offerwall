package com.example.iapgame.racingcar_game.domain.repo

import kotlinx.coroutines.flow.Flow

interface HighscoreRepository {

    suspend fun saveHighScore(score: Int)

    fun getHighScore(): Flow<Int>
}