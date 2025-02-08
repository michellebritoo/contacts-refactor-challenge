package com.contacts.desafio.android

import retrofit2.Call
import retrofit2.http.GET

interface Service {

    @GET("users")
    fun getUsers(): Call<List<User>>
}