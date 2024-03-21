package com.example.iapgame.racingcar_game.domain.usecase

import com.example.iapgame.racingcar_game.ui.viewmodel.MainViewModel
import com.example.iapgame.racingcar_game.utils.SoundRepository
import com.example.iapgame.racingcar_game.domain.repo.HighscoreRepository
import kotlinx.coroutines.flow.first

class SaveHighscoreUseCase(
    private val highscoreRepository: HighscoreRepository,
    private val soundRepository: SoundRepository
) {

    suspend fun execute(score: Int) {
        val currentHighscore = highscoreRepository.getHighScore().first()
        if (score > currentHighscore) {
            highscoreRepository.saveHighScore(score)
            soundRepository.playSound(MainViewModel.NEW_HIGHSCORE_SOUND_ID)
        }
    }
}