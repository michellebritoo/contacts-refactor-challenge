package com.contacts.desafio.android.domain.usecases

import com.contacts.desafio.android.data.UsersRepository
import com.contacts.desafio.android.data.local.entity.UserEntity

class SaveUsersUseCase(private val repository: UsersRepository) {
    suspend operator fun invoke(users: List<UserEntity>) = repository.saveUsers(users)
}
