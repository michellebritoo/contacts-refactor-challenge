package com.contacts.desafio.android.di

import com.contacts.desafio.android.domain.usecases.GetLocalUsersUseCase
import com.contacts.desafio.android.domain.usecases.GetUsersUseCase
import com.contacts.desafio.android.domain.usecases.SaveUsersUseCase
import com.contacts.desafio.android.domain.usecases.UsersUseCases
import org.koin.dsl.module

object DomainModule {
    val instance = module {
        factory { GetUsersUseCase(get()) }
        factory { GetLocalUsersUseCase(get()) }
        factory { SaveUsersUseCase(get()) }
        factory { UsersUseCases(get(), get(), get()) }
    }
}