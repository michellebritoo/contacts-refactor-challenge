package com.contacts.desafio.android.di

import com.contacts.desafio.android.domain.usecases.GetUsersUseCase
import org.koin.dsl.module

object DomainModule {
    val instance = module {
        factory { GetUsersUseCase(get()) }
    }
}