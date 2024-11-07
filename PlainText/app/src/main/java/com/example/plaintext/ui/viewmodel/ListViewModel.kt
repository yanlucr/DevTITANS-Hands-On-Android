package com.example.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.plaintext.data.model.Password
import com.example.plaintext.data.repository.PasswordDBStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ListViewState(
    val passwords: List<Password> = emptyList(),
    val isLoading: Boolean = true
)

@HiltViewModel
class ListViewModel @Inject constructor(
    private val passwordDBStore: PasswordDBStore
) : ViewModel() {

    private val _viewState = MutableStateFlow(ListViewState())
    val viewState: StateFlow<ListViewState> get() = _viewState

    init {
        fetchPasswords()
    }

    private fun fetchPasswords() {
        viewModelScope.launch {
            passwordDBStore.getAllPasswords().collect { passwords ->
                _viewState.value = ListViewState(passwords = passwords, isLoading = false)
            }
        }
    }

    fun addPassword(password: Password) {
        viewModelScope.launch {
            passwordDBStore.insertPassword(password)
        }
    }

    fun deletePassword(password: Password) {
        viewModelScope.launch {
            passwordDBStore.deletePassword(password)
        }
    }
}
