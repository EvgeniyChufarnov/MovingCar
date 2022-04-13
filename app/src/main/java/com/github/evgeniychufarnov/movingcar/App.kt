package com.github.evgeniychufarnov.movingcar

import android.app.Application
import com.github.evgeniychufarnov.movingcar.di.mainModule
import org.koin.core.context.GlobalContext.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            modules(mainModule)
        }
    }
}