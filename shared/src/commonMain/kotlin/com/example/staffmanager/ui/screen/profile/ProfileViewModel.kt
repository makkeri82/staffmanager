package com.example.staffmanager.ui.screen.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.staffmanager.mockData.User
import com.example.staffmanager.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class ProfileUiState(val user: User? = null)

sealed interface ProfileAction {
    data object NavigateBack : ProfileAction
}

class ProfileViewModel(private val userRepository: UserRepository) : ViewModel() {
    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            _uiState.update { it.copy(user = userRepository.getUserById(1)) }
        }
    }

    fun onAction(action: ProfileAction) {}
}
