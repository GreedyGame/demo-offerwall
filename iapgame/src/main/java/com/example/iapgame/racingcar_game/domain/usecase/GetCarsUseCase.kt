package com.example.iapgame.racingcar_game.domain.usecase

import android.util.Log
import com.example.iapgame.racingcar_game.domain.repo.CarsRepository
import com.example.iapgame.racingcar_game.ui.shop.CarInfo

class GetCarsUseCase(private val carsRepository: CarsRepository) {
    suspend fun execute(): List<CarInfo> = carsRepository.getCars()
    suspend fun selectedCar(): CarInfo {
        return execute().first {
            it.carId == carsRepository.getSelectedCar()
        }
    }
}