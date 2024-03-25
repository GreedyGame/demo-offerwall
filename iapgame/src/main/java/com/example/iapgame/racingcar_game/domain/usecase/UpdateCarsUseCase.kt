package com.example.iapgame.racingcar_game.domain.usecase

import com.example.iapgame.racingcar_game.domain.repo.CarsRepository
import com.example.iapgame.racingcar_game.ui.shop.CarInfo

class UpdateCarsUseCase(private val carsRepository: CarsRepository) {
    suspend fun changeSelectedCar(carInfo: CarInfo) = carsRepository.changeSelectedCar(carInfo)
    suspend fun buyCar(carInfo: CarInfo) = carsRepository.buyCar(carInfo)
}