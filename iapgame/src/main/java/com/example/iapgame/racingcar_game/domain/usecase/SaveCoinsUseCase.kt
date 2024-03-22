package com.example.iapgame.racingcar_game.domain.usecase

import com.example.iapgame.racingcar_game.domain.repo.CoinsRepository
import kotlinx.coroutines.flow.first

class SaveCoinsUseCase(
    private val coinsRepository: CoinsRepository,
) {

    suspend fun credit(coins: Int) {
        val availableCoins = coinsRepository.getCoins().first()
        coinsRepository.saveCoins(availableCoins + coins)
    }

    suspend fun debit(coins: Int) {
        val availableCoins = coinsRepository.getCoins().first()
        coinsRepository.saveCoins(availableCoins - coins)
    }
}