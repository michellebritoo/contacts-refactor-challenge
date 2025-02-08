package com.contacts.desafio.android.presentation

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.contacts.desafio.android.presentation.adapter.UserListAdapter
import com.contacts.desafio.android.presentation.adapter.UserUIModel
import com.contacts.desafio.android.presentation.viewmodel.UsersUIEvent
import com.contacts.desafio.android.presentation.viewmodel.UsersViewModel
import com.list.desafio.android.R
import com.list.desafio.android.databinding.ActivityMainBinding
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity(R.layout.activity_main) {
    private val viewModel: UsersViewModel by inject()
    private val binding: ActivityMainBinding by viewBinding()
    private val adapter: UserListAdapter by lazy { UserListAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        observeEvents()
        viewModel.onStart()
    }

    private fun observeEvents() = lifecycleScope.launch {
        repeatOnLifecycle(Lifecycle.State.CREATED) {
            viewModel.viewState.collect { event ->
                when (event) {
                    is UsersUIEvent.Loader -> loader(event.shouldShowLoad)
                    is UsersUIEvent.ShowError -> showError()
                    is UsersUIEvent.ShowUsersList -> showUserList(event.list)
                }
            }
        }
    }

    private fun loader(shouldShowLoading: Boolean) {
        binding.userListProgressBar.isVisible = shouldShowLoading
    }

    private fun showUserList(list: List<UserUIModel>) {
        setupList()
        adapter.users = list
    }

    private fun showError() {
        Toast.makeText(
            this,
            getString(R.string.error),
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun setupList() = binding.recyclerView.let {
        it.adapter = adapter
        it.layoutManager = LinearLayoutManager(this@MainActivity)
    }
}
