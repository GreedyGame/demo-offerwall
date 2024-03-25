package com.example.iapgame.racingcar_game.domain.usecase

import com.example.iapgame.racingcar_game.domain.repo.CoinsRepository
import kotlinx.coroutines.flow.first

class SaveCoinsUseCase(
    private val coinsRepository: CoinsRepository,
) {

    suspend fun credit(coins: Int) {
        val balance = coinsRepository.getCoins().first() + coins
        coinsRepository.saveCoins(balance)
    }

    suspend fun debit(coins: Int) {
        val balance = coinsRepository.getCoins().first() - coins
        coinsRepository.saveCoins(balance)
    }
}