package com.example.tableo.di

import android.app.Application
import com.example.tableo.util.AppStrings
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

/** Startet Koin beim App-Start, mit Context für GPS, Benachrichtigungen und DataStore. */
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        AppStrings.context = this

        startKoin {
            androidContext(this@App)
            modules(appModule)
        }
    }
}
