package com.contacts.desafio.android.common.ext

import com.contacts.desafio.android.data.local.entity.UserEntity
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

fun List<UserEntity>.entityToUIModel(): List<UserUIModel> {
    return map { user ->
        UserUIModel(
            name = user.name.orEmpty(),
            username = user.username.orEmpty(),
            image = user.image.orEmpty()
        )
    }
}

fun List<UserResponse>.responseToEntity(): List<UserEntity> {
    return map { user ->
        UserEntity(
            id = user.id ?: 0,
            name = user.name.orEmpty(),
            username = user.username.orEmpty(),
            image = user.image.orEmpty()
        )
    }
}