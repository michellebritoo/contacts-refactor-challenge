package com.contacts.desafio.android.di

import com.contacts.desafio.android.presentation.viewmodel.UsersViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object PresentationModule {
    val instance = module {
        viewModel { UsersViewModel(get()) }
    }
}
