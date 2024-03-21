package com.example.iapgame.di

import com.example.iapgame.racingcar_game.data.repo.HighscoreRepositoryImpl
import com.example.iapgame.racingcar_game.domain.repo.HighscoreRepository
import com.example.iapgame.racingcar_game.domain.usecase.GetHighscoreUseCase
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
    factoryOf(::SoundRepository1)
    factoryOf(::GetHighscoreUseCase)
    factoryOf(::SaveHighscoreUseCase1)
}