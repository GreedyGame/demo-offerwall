package com.example.iapgame.di

import com.example.iapgame.racingcar_game.data.repo.CoinsRepositoryImpl
import com.example.iapgame.racingcar_game.data.repo.HighscoreRepositoryImpl
import com.example.iapgame.racingcar_game.data.repo.ShopCarsRepositoryImpl
import com.example.iapgame.racingcar_game.domain.repo.CoinsRepository
import com.example.iapgame.racingcar_game.domain.repo.HighscoreRepository
import com.example.iapgame.racingcar_game.domain.repo.ShopCarsRepository
import com.example.iapgame.racingcar_game.domain.usecase.GetCoinsUseCase
import com.example.iapgame.racingcar_game.domain.usecase.GetHighscoreUseCase
import com.example.iapgame.racingcar_game.domain.usecase.SaveCoinsUseCase
import com.example.iapgame.racingcar_game.domain.usecase.ShopCarsUseCase
import com.example.iapgame.racingcar_game.ui.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import com.example.iapgame.racingcar_game.domain.usecase.SaveHighscoreUseCase as SaveHighscoreUseCase1
import com.example.iapgame.racingcar_game.utils.SoundRepository as SoundRepository1

val parentModule = module {
    viewModelOf(::MainViewModel)
    singleOf(::HighscoreRepositoryImpl) { bind<HighscoreRepository>() }
    singleOf(::CoinsRepositoryImpl) { bind<CoinsRepository>() }
    singleOf(::ShopCarsRepositoryImpl) { bind<ShopCarsRepository>() }
    factoryOf(::SoundRepository1)
    factoryOf(::GetHighscoreUseCase)
    factoryOf(::SaveHighscoreUseCase1)
    factoryOf(::GetCoinsUseCase)
    factoryOf(::SaveCoinsUseCase)
    factoryOf(::ShopCarsUseCase)
}