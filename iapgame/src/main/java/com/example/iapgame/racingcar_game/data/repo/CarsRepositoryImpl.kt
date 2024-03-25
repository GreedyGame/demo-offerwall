package com.example.iapgame.racingcar_game.data.repo

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.iapgame.R
import com.example.iapgame.racingcar_game.data.source.cars
import com.example.iapgame.racingcar_game.domain.repo.CarsRepository
import com.example.iapgame.racingcar_game.ui.shop.CarInfo
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class CarsRepositoryImpl(val context: Context) : CarsRepository {

    companion object {
        val SELECTED_CAR_KEY = stringPreferencesKey("selected_car_key")
    }

    override suspend fun getSelectedCar(): String {
        return context.cars.data.map {
            it[SELECTED_CAR_KEY] ?: "car_0"
        }.first()
    }

    override suspend fun buyCar(carInfo: CarInfo) {
        context.cars.edit {
            it[booleanPreferencesKey(carInfo.carId)] = true
        }
    }

    override suspend fun changeSelectedCar(carInfo: CarInfo) {
        context.cars.edit {
            it[SELECTED_CAR_KEY] = carInfo.carId
        }
    }

    override suspend fun getCars(): List<CarInfo> {
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
                carId = "car_1",
                carImage = R.drawable.ic_car_1,
                carCost = 50,
                carIsOwned = getIsCarOwned("car_1")
            )
        )
        cars.add(
            CarInfo(
                carId = "car_2",
                carImage = R.drawable.ic_car_2,
                carCost = 50,
                carIsOwned = getIsCarOwned("car_2")
            )
        )
        cars.add(
            CarInfo(
                carId = "car_3",
                carImage = R.drawable.ic_car_3,
                carCost = 150,
                carIsOwned = getIsCarOwned("car_3")
            )
        )
        cars.add(
            CarInfo(
                carId = "car_4",
                carImage = R.drawable.ic_car_4,
                carCost = 400,
                carIsOwned = getIsCarOwned("car_4")
            )
        )
        cars.add(
            CarInfo(
                carId = "car_5",
                carImage = R.drawable.ic_car_5,
                carCost = 500,
                carIsOwned = getIsCarOwned("car_5")
            )
        )
        return cars.map {
            it.copy(isSelected = getSelectedCar() == it.carId)
        }
    }

    private suspend fun getIsCarOwned(carId: String): Boolean {
        return context.cars.data.map {
            it[booleanPreferencesKey(carId)] ?: false
        }.first()
    }
}