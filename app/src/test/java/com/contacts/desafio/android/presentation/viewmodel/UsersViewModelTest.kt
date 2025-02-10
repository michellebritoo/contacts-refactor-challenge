package com.contacts.desafio.android.presentation.viewmodel

import app.cash.turbine.test
import com.contacts.desafio.android.TestCoroutineRule
import com.contacts.desafio.android.coVerifyNever
import com.contacts.desafio.android.common.ext.entityToUIModel
import com.contacts.desafio.android.common.ext.responseToUIModel
import com.contacts.desafio.android.data.local.entity.UserEntity
import com.contacts.desafio.android.data.remote.UserResponse
import com.contacts.desafio.android.domain.models.Result
import com.contacts.desafio.android.domain.usecases.UsersUseCases
import com.contacts.desafio.android.relaxedMock
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class UsersViewModelTest {

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    private val userUseCases: UsersUseCases = relaxedMock()
    private val viewModel = UsersViewModel(userUseCases)

    @Before
    fun setup() = clearAllMocks()

    @Test
    fun `onStart with success api response should show list and save data on local storage`() = runTest {
        val usersList = usersResponseStub()
        coEvery { userUseCases.getUsers() } returns flow { emit(usersList) }

        viewModel.onStart()
        advanceUntilIdle()

        viewModel.viewState.test {
            assertEquals(UsersUIEvent.Loader(shouldShowLoad = true), awaitItem())
            assertEquals(UsersUIEvent.ShowUsersList(usersList.responseToUIModel()), awaitItem())
            assertEquals(UsersUIEvent.Loader(shouldShowLoad = false), awaitItem())
        }

        coVerify {
            userUseCases.getUsers()
            userUseCases.saveUsers(any())
        }
        coVerifyNever { userUseCases.getLocalUsers() }
    }

    @Test
    fun `onStart with failure api response should show list and find data on local storage`() = runTest {
        coEvery { userUseCases.getUsers() } returns flow { throw Throwable("failure") }
        coEvery { userUseCases.getLocalUsers() } returns flow {
            emit(Result.Success(usersEntityStub()))
        }

        viewModel.onStart()
        advanceUntilIdle()

        viewModel.viewState.test {
            assertEquals(UsersUIEvent.Loader(shouldShowLoad = true), awaitItem())
            assertEquals(UsersUIEvent.Loader(shouldShowLoad = false), awaitItem())
            assertEquals(UsersUIEvent.Loader(shouldShowLoad = true), awaitItem())
            assertEquals(UsersUIEvent.ShowUsersList(usersEntityStub().entityToUIModel()), awaitItem())
            assertEquals(UsersUIEvent.Loader(shouldShowLoad = false), awaitItem())
        }

        coVerify {
            userUseCases.getUsers()
            userUseCases.getLocalUsers()
        }
        coVerifyNever { userUseCases.saveUsers(any()) }
    }

    private fun usersResponseStub() = listOf(
        UserResponse(id = 0, image = "http://image", name = "name", username = "username"),
        UserResponse(id = 1, image = "http://image2", name = "other name", username = "other username")
    )

    private fun usersEntityStub() = listOf(
        UserEntity(id = 0, image = "http://image", name = "name", username = "username"),
        UserEntity(id = 1, image = "http://image2", name = "other name", username = "other username")
    )
}
