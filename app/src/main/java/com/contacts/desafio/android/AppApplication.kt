package com.contacts.desafio.android

import android.app.Application
import com.contacts.desafio.android.di.PresentationModule
import com.contacts.desafio.android.di.DataModule
import com.contacts.desafio.android.di.DomainModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class AppApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@AppApplication)
            modules(
                DataModule.instance,
                DomainModule.instance,
                PresentationModule.instance
            )
        }
    }
}
