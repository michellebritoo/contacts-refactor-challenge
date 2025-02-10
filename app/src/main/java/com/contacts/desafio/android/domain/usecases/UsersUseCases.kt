package com.contacts.desafio.android.domain.usecases

data class UsersUseCases(
    val getUsers: GetUsersUseCase,
    val saveUsers: SaveUsersUseCase,
    val getLocalUsers: GetLocalUsersUseCase
)