package com.contacts.desafio.android.data

import com.contacts.desafio.android.coVerifyNever
import com.contacts.desafio.android.data.local.dao.UserDao
import com.contacts.desafio.android.data.local.entity.UserEntity
import com.contacts.desafio.android.data.remote.UserResponse
import com.contacts.desafio.android.data.remote.UsersClient
import com.contacts.desafio.android.relaxedMock
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class UsersRepositoryTest {
    private val client: UsersClient = relaxedMock()
    private val userDao: UserDao = relaxedMock()
    private val repository = UsersRepositoryImpl(client, userDao)

    @Before
    fun setup() = clearAllMocks()

    @Test
    fun `getUsers should return users from remote source`() = runTest {
        coEvery { client.getUsers() } returns usersResponseStub()

        val users = repository.getAllUsers().first()

        assertEquals(usersResponseStub(), users)
        coVerify { client.getUsers() }
        coVerifyNever { userDao.getAllUsers() }
    }

    @Test
    fun `getAllUserFromLocalStorage should return users from local source`() = runTest {
        coEvery { userDao.getAllUsers() } returns usersEntityStub()

        val users = repository.getAllUserFromLocalStorage().first()

        assertEquals(usersEntityStub(), users)
        coVerify { userDao.getAllUsers() }
        coVerifyNever { client.getUsers() }
    }

    @Test
    fun `saveUser should insert users into local source`() = runTest {
        repository.saveUsers(usersEntityStub())

        coVerify { userDao.insertUsers(usersEntityStub()) }
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
