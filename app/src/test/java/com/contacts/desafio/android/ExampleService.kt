package com.contacts.desafio.android

class ExampleService(
    private val service: Service
) {

    fun example(): List<User> {
        val users = service.getUsers().execute()

        return users.body() ?: emptyList()
    }
}