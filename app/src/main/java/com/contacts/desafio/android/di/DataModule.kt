package com.contacts.desafio.android.di

import androidx.room.Room
import com.contacts.desafio.android.data.UsersRepository
import com.contacts.desafio.android.data.UsersRepositoryImpl
import com.contacts.desafio.android.data.local.AppDataBase
import com.contacts.desafio.android.data.local.dao.UserDao
import com.contacts.desafio.android.data.remote.RetrofitInstance
import com.contacts.desafio.android.data.remote.UsersClient
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

object DataModule {
    val instance = module {
        factory { RetrofitInstance.retrofitInstance.create(UsersClient::class.java) }
        factory<UsersRepository> { UsersRepositoryImpl(get(), get()) }

        single<AppDataBase> {
            Room.databaseBuilder(
                androidContext(),
                AppDataBase::class.java,
                "app_database"
            ).build()
        }
        single<UserDao> { get<AppDataBase>().userDao() }
    }
}
