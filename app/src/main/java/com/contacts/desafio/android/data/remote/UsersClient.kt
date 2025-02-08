package com.contacts.desafio.android.data.remote

import retrofit2.http.GET

interface UsersClient {

    @GET(USERS)
    suspend fun getUsers(): List<UserResponse>

    private companion object Routes {
        const val USERS = "users"
    }
}