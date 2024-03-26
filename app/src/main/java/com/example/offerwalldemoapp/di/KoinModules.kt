package com.example.offerwalldemoapp.di

import com.example.navigation.AppCoordinator
import com.example.navigation.AppNavigator
import com.example.offerwalldemoapp.ui.dashboard.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val parentModule = module {
    singleOf(::AppCoordinator) { bind<AppNavigator>() }
    viewModelOf(::MainViewModel)
}