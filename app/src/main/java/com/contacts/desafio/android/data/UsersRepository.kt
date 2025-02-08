package com.contacts.desafio.android.data

import com.contacts.desafio.android.data.local.dao.UserDao
import com.contacts.desafio.android.data.local.entity.UserEntity
import com.contacts.desafio.android.data.remote.UserResponse
import com.contacts.desafio.android.data.remote.UsersClient
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

interface UsersRepository {
    suspend fun getAllUsers(): Flow<List<UserResponse>>
    suspend fun getAllUserFromLocalStorage(): Flow<List<UserEntity>>
    suspend fun saveUsers(users: List<UserEntity>)
}

class UsersRepositoryImpl(
    private val client: UsersClient,
    private val userDao: UserDao
): UsersRepository {
    override suspend fun getAllUsers(): Flow<List<UserResponse>> = flow {
        emit(client.getUsers())
    }

    override suspend fun getAllUserFromLocalStorage(): Flow<List<UserEntity>> = flow {
        emit(userDao.getAllUsers())
    }

    override suspend fun saveUsers(users: List<UserEntity>) {
        userDao.insertUsers(users)
    }
}