package com.contacts.desafio.android

import android.app.Application
import com.contacts.desafio.android.di.AppModule
import com.contacts.desafio.android.di.DataModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class AppApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@AppApplication)
            modules(
                AppModule.instance,
                DataModule.instance
            )
        }
    }
}
