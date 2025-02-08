package com.contacts.desafio.android.domain.usecases

import com.contacts.desafio.android.data.UsersRepository
import com.contacts.desafio.android.data.local.entity.UserEntity
import com.contacts.desafio.android.domain.models.Result
import kotlinx.coroutines.flow.Flow

import kotlinx.coroutines.flow.flow

class GetLocalUsersUseCase(private val repository: UsersRepository) {
    suspend operator fun invoke(): Flow<Result<List<UserEntity>>> = flow {
        val users = repository.getAllUserFromLocalStorage()

        users.collect { userList ->
            if (userList.isEmpty()) {
                emit(Result.Error("Empty"))
            } else {
                emit(Result.Success(userList))
            }
        }
    }
}
