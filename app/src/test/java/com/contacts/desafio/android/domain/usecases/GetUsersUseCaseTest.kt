package com.contacts.desafio.android.domain.usecases

import com.contacts.desafio.android.data.UsersRepository
import com.contacts.desafio.android.data.remote.UserResponse
import com.contacts.desafio.android.relaxedMock
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class GetUsersUseCaseTest {
    private val repository: UsersRepository = relaxedMock()
    private val useCase = GetUsersUseCase(repository)

    @Before
    fun setup() = clearAllMocks()

    @Test
    fun `get users should call repository get all users`() = runTest {
        coEvery { repository.getAllUsers() } returns flow { emit(usersResponseStub()) }

        val result = useCase().first()

        assertEquals(usersResponseStub(), result)
        coVerify { repository.getAllUsers() }
    }

    private fun usersResponseStub() = listOf(
        UserResponse(id = 0, image = "http://image", name = "name", username = "username"),
        UserResponse(id = 1, image = "http://image2", name = "other name", username = "other username")
    )
}
