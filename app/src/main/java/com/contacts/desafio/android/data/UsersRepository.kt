package com.contacts.desafio.android.data

import com.contacts.desafio.android.data.remote.UserResponse
import com.contacts.desafio.android.data.remote.UsersClient
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

interface UsersRepository {
    suspend fun getAllUsers(): Flow<List<UserResponse>>
}

class UsersRepositoryImpl(private val client: UsersClient): UsersRepository {
    override suspend fun getAllUsers(): Flow<List<UserResponse>> = flow {
        emit(client.getUsers())
    }
}