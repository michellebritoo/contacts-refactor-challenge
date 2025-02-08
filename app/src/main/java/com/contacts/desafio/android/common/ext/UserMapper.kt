package com.contacts.desafio.android.common.ext

import com.contacts.desafio.android.data.remote.UserResponse
import com.contacts.desafio.android.presentation.adapter.UserUIModel

fun List<UserResponse>.responseToUIModel(): List<UserUIModel> {
    return map { user ->
        UserUIModel(
            name = user.name.orEmpty(),
            username = user.username.orEmpty(),
            image = user.image.orEmpty()
        )
    }
}