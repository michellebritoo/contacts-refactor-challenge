package com.contacts.desafio.android.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.contacts.desafio.android.common.ext.responseToUIModel
import com.contacts.desafio.android.domain.usecases.GetUsersUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class UsersViewModel(
    private val getUsersUseCase: GetUsersUseCase
) : ViewModel() {
    private val _viewState = MutableSharedFlow<UsersUIEvent>()
    val viewState get() = _viewState.asSharedFlow()

    fun onStart() = viewModelScope.launch(Dispatchers.IO) {
        getUsersUseCase()
            .onStart { sendUIEvent(UsersUIEvent.Loader(shouldShowLoad = true)) }
            .onCompletion { sendUIEvent(UsersUIEvent.Loader(shouldShowLoad = false)) }
            .catch {  }
            .collect { response ->
                sendUIEvent(
                    UsersUIEvent.ShowUsersList(response.responseToUIModel())
                )
            }
    }

    private fun sendUIEvent(event: UsersUIEvent) = viewModelScope.launch(Dispatchers.Main) {
        _viewState.emit(event)
    }
}
