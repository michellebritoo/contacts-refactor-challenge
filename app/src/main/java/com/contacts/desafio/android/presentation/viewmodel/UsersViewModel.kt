package com.contacts.desafio.android.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.contacts.desafio.android.common.ext.entityToUIModel
import com.contacts.desafio.android.common.ext.responseToEntity
import com.contacts.desafio.android.common.ext.responseToUIModel
import com.contacts.desafio.android.data.remote.UserResponse
import com.contacts.desafio.android.domain.models.Result.*
import com.contacts.desafio.android.domain.usecases.UsersUseCases
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class UsersViewModel(
    private val usersUseCases: UsersUseCases
) : ViewModel() {
    private val _viewState = MutableSharedFlow<UsersUIEvent>()
    val viewState get() = _viewState.asSharedFlow()

    fun onStart() = viewModelScope.launch(Dispatchers.IO) {
        usersUseCases.getUsers()
            .onStart { sendUIEvent(UsersUIEvent.Loader(shouldShowLoad = true)) }
            .onCompletion { sendUIEvent(UsersUIEvent.Loader(shouldShowLoad = false)) }
            .catch { getLocalData() }
            .collect { response ->
                sendUIEvent(
                    UsersUIEvent.ShowUsersList(response.responseToUIModel())
                )
                saveLocalData(response)
            }
    }

    private suspend fun saveLocalData(response: List<UserResponse>) {
        usersUseCases.saveUsers(response.responseToEntity())
    }

    private suspend fun getLocalData() {
        usersUseCases.getLocalUsers()
            .onStart { sendUIEvent(UsersUIEvent.Loader(shouldShowLoad = true)) }
            .onCompletion { sendUIEvent(UsersUIEvent.Loader(shouldShowLoad = false)) }
            .catch { sendUIEvent(UsersUIEvent.ShowError) }
            .collect { result ->
                if (result is Success) {
                    sendUIEvent(UsersUIEvent.ShowUsersList(result.data.entityToUIModel()))
                } else {
                    sendUIEvent(UsersUIEvent.ShowError)
                }
            }
    }

    private fun sendUIEvent(event: UsersUIEvent) = viewModelScope.launch(Dispatchers.Main) {
        _viewState.emit(event)
    }
}
