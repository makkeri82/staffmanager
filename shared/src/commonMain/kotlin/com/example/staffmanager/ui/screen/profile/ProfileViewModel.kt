package com.example.staffmanager.ui.screen.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.staffmanager.di.SessionState
import com.example.staffmanager.mockData.User
import com.example.staffmanager.repository.AuthRepository
import com.example.staffmanager.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class ProfileUiState(
    val user: User? = null,
    val isLoading: Boolean = true
)

sealed interface ProfileAction {
    data object Logout : ProfileAction
}

class ProfileViewModel(
    private val userRepository: UserRepository,
    private val authRepository: AuthRepository,
    private val sessionState: SessionState
) : ViewModel() {
    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            val email = authRepository.getEmail()
            val user = if (email != null) {
                userRepository.getUsers().find { it.email == email }
            } else {
                userRepository.getUserById(1)
            }
            _uiState.update { it.copy(user = user, isLoading = false) }
        }
    }

    fun onAction(action: ProfileAction) {
        when (action) {
            ProfileAction.Logout -> {
                authRepository.clearSession()
                sessionState.role = null
                sessionState.isLoggedIn = false
            }
        }
    }
}
