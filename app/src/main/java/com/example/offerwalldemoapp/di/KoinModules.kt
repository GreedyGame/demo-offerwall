package com.example.offerwalldemoapp.di

import com.example.navigation.AppCoordinator
import com.example.navigation.AppNavigator
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val parentModule = module {
    singleOf(::AppCoordinator) { bind<AppNavigator>() }
}