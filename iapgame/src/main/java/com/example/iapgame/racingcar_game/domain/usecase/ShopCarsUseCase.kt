package com.example.iapgame.racingcar_game.domain.usecase

import com.example.iapgame.R
import com.example.iapgame.racingcar_game.ui.shop.CarInfo
import kotlinx.coroutines.flow.Flow

class ShopCarsUseCase {
    fun getShopCars(): MutableList<CarInfo> {
        val cars = mutableListOf<CarInfo>()
        cars.add(
            CarInfo(
                carId = "car_0",
                carImage = R.drawable.ic_car,
                carCost = 0,
                carIsOwned = true,
                isSelected = true
            )
        )
        cars.add(
            CarInfo(
                carId = "car_1", carImage = R.drawable.ic_car_1, carCost = 50, carIsOwned = false
            )
        )
        cars.add(
            CarInfo(
                carId = "car_2", carImage = R.drawable.ic_car_2, carCost = 50, carIsOwned = false
            )
        )
        cars.add(
            CarInfo(
                carId = "car_3", carImage = R.drawable.ic_car_3, carCost = 150, carIsOwned = false
            )
        )
        cars.add(
            CarInfo(
                carId = "car_4", carImage = R.drawable.ic_car_4, carCost = 400, carIsOwned = false
            )
        )
        cars.add(
            CarInfo(
                carId = "car_5", carImage = R.drawable.ic_car_5, carCost = 500, carIsOwned = false
            )
        )
        return cars
    }
}