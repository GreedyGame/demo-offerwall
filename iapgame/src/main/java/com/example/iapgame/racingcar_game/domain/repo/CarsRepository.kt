package com.example.iapgame.racingcar_game.domain.repo

import com.example.iapgame.racingcar_game.ui.shop.CarInfo

interface CarsRepository {
    suspend fun getSelectedCar(): String
    suspend fun buyCar(carInfo: CarInfo)
    suspend fun changeSelectedCar(carInfo: CarInfo)
    suspend fun getCars(): List<CarInfo>
}