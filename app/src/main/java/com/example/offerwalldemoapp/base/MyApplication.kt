package com.example.offerwalldemoapp.base

import android.app.Application
import com.example.offerwalldemoapp.di.parentModule
import org.koin.core.context.startKoin

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(parentModule)
        }
    }
}