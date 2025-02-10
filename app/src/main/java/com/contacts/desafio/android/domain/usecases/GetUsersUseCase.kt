package com.contacts.desafio.android.domain.usecases

import com.contacts.desafio.android.data.UsersRepository
import com.contacts.desafio.android.data.remote.UserResponse
import kotlinx.coroutines.flow.Flow

class GetUsersUseCase(private val repository: UsersRepository) {
    suspend operator fun invoke(): Flow<List<UserResponse>> = repository.getAllUsers()
}