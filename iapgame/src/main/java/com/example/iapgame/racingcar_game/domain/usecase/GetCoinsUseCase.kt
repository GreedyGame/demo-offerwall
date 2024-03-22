package com.example.iapgame.racingcar_game.domain.usecase

import com.example.iapgame.racingcar_game.domain.repo.CoinsRepository
import kotlinx.coroutines.flow.Flow

class GetCoinsUseCase(
    private val coinsRepository: CoinsRepository,
) {
    fun execute(): Flow<Int> = coinsRepository.getCoins()
}