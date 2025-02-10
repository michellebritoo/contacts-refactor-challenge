package com.contacts.desafio.android.domain.usecases

import com.contacts.desafio.android.data.UsersRepository
import com.contacts.desafio.android.data.local.entity.UserEntity
import com.contacts.desafio.android.relaxedMock
import com.contacts.desafio.android.domain.models.Result
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class GetLocalUsersUseCaseTest {
    private val repository: UsersRepository = relaxedMock()
    private val useCase = GetLocalUsersUseCase(repository)

    @Before
    fun setup() = clearAllMocks()

    @Test
    fun `when get local users returns empty list should emit error result`() = runTest {
        coEvery { repository.getAllUserFromLocalStorage() } returns flow { emit(emptyList()) }

        val result = useCase().first()

        assertEquals(Result.Error("Empty"), result)
        coVerify { repository.getAllUserFromLocalStorage() }
    }

    @Test
    fun `when get local users returns user list should emit success result`() = runTest {
        coEvery { repository.getAllUserFromLocalStorage() } returns flow { emit(usersEntityStub()) }

        val result = useCase().first()

        assertEquals(Result.Success(usersEntityStub()), result)
        coVerify { repository.getAllUserFromLocalStorage() }
    }

    private fun usersEntityStub() = listOf(
        UserEntity(id = 0, image = "http://image", name = "name", username = "username"),
        UserEntity(id = 1, image = "http://image2", name = "other name", username = "other username")
    )
}
