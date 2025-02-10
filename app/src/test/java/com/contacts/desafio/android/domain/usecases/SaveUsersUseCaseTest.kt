package com.contacts.desafio.android.domain.usecases

import com.contacts.desafio.android.data.UsersRepository
import com.contacts.desafio.android.data.local.entity.UserEntity
import com.contacts.desafio.android.data.remote.UserResponse
import com.contacts.desafio.android.domain.models.Result
import com.contacts.desafio.android.relaxedMock
import com.nhaarman.mockitokotlin2.any
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class SaveUsersUseCaseTest {
    private val repository: UsersRepository = relaxedMock()
    private val useCase = SaveUsersUseCase(repository)

    @Before
    fun setup() = clearAllMocks()

    @Test
    fun `save users should insert users into local storage`() = runTest {
        useCase(usersEntityStub())

        coVerify { repository.saveUsers(usersEntityStub()) }
    }

    private fun usersEntityStub() = listOf(
        UserEntity(id = 0, image = "http://image", name = "name", username = "username"),
        UserEntity(id = 1, image = "http://image2", name = "other name", username = "other username")
    )
}
