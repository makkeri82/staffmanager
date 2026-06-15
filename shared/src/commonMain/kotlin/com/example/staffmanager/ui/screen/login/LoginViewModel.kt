package com.example.staffmanager.ui.screen.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.staffmanager.di.SessionState
import com.example.staffmanager.repository.AuthRepository
import com.example.staffmanager.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val error: String? = null
)

sealed interface LoginAction {
    data class UpdateEmail(val value: String) : LoginAction
    data class UpdatePassword(val value: String) : LoginAction
    data object Login : LoginAction
}

class LoginViewModel(
    private val userRepository: UserRepository,
    private val authRepository: AuthRepository,
    private val sessionState: SessionState
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    fun onAction(action: LoginAction) {
        when (action) {
            is LoginAction.UpdateEmail -> _uiState.update { it.copy(email = action.value, error = null) }
            is LoginAction.UpdatePassword -> _uiState.update { it.copy(password = action.value, error = null) }
            LoginAction.Login -> login()
        }
    }

    private fun login() {
        val email = _uiState.value.email.trim()
        val password = _uiState.value.password
        if (email.isBlank() || password.isBlank()) {
            _uiState.update { it.copy(error = "Please enter email and password") }
            return
        }
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            val user = userRepository.login(email, password)
            if (user != null) {
                authRepository.saveSession("jwt_${user.id}", user.email, user.userLevel)
                sessionState.role = user.userLevel
                _uiState.update { LoginUiState() }
                sessionState.isLoggedIn = true
            } else {
                _uiState.update { it.copy(isLoading = false, error = "Invalid email or password") }
            }
        }
    }
}
