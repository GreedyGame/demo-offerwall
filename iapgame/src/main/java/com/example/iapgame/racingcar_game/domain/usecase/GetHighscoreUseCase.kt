package com.example.iapgame.racingcar_game.domain.usecase

import com.example.iapgame.racingcar_game.domain.repo.HighscoreRepository
import kotlinx.coroutines.flow.Flow

class GetHighscoreUseCase(
    private val highscoreRepository: HighscoreRepository,
) {
    fun execute(): Flow<Int> = highscoreRepository.getHighScore()
}