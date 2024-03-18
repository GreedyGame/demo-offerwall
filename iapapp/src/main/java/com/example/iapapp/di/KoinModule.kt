package com.example.iapapp.di

import com.example.iapapp.ui.details.BookDetailsViewModel
import com.example.iapapp.ui.home.HomeViewModel
import com.example.iapapp.utils.AppPreferences
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val parentModule = module {
    viewModelOf(::HomeViewModel)
    viewModelOf(::BookDetailsViewModel)
    singleOf(::AppPreferences)
}