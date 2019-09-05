package com.andremion.bikes

import android.app.Application
import com.andremion.bikes.di.dataModule
import com.andremion.bikes.di.homeModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class BikesApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@BikesApplication)
            modules(homeModule, dataModule)
        }
    }
}