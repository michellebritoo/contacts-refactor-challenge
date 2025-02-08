package com.contacts.desafio.android.di

import com.contacts.desafio.android.data.UsersRepository
import com.contacts.desafio.android.data.UsersRepositoryImpl
import com.contacts.desafio.android.data.remote.RetrofitInstance
import com.contacts.desafio.android.data.remote.UsersClient
import org.koin.dsl.module

object DataModule {
    val instance = module {
        factory { RetrofitInstance.retrofitInstance.create(UsersClient::class.java) }
        factory<UsersRepository> { UsersRepositoryImpl(get()) }
    }
}
