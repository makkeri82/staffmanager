package com.example.staffmanager.ui.screen.login

import com.example.staffmanager.ViewModelTest
import com.example.staffmanager.di.SessionState
import com.example.staffmanager.fakes.FakeAuthRepository
import com.example.staffmanager.repository.MockUserRepositoryImpl
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

class LoginViewModelTest : ViewModelTest() {

    private lateinit var viewModel: LoginViewModel
    private lateinit var fakeAuth: FakeAuthRepository
    private lateinit var sessionState: SessionState

    @BeforeTest
    fun setUp() {
        fakeAuth = FakeAuthRepository()
        sessionState = SessionState()
        viewModel = LoginViewModel(
            userRepository = MockUserRepositoryImpl(),
            authRepository = fakeAuth,
            sessionState = sessionState
        )
    }

    @Test
    fun `initial state has empty email and password with no error`() {
        // Assert
        val state = viewModel.uiState.value
        assertEquals("", state.email)
        assertEquals("", state.password)
        assertFalse(state.isLoading)
        assertNull(state.error)
    }

    @Test
    fun `UpdateEmail updates email field and clears error`() {
        // Arrange
        viewModel.onAction(LoginAction.Login) // trigger an error first

        // Act
        viewModel.onAction(LoginAction.UpdateEmail("test@example.com"))

        // Assert
        assertEquals("test@example.com", viewModel.uiState.value.email)
        assertNull(viewModel.uiState.value.error)
    }

    @Test
    fun `UpdatePassword updates password field and clears error`() {
        // Arrange
        viewModel.onAction(LoginAction.Login) // trigger an error first

        // Act
        viewModel.onAction(LoginAction.UpdatePassword("secret"))

        // Assert
        assertEquals("secret", viewModel.uiState.value.password)
        assertNull(viewModel.uiState.value.error)
    }

    @Test
    fun `Login with blank email shows validation error`() {
        // Arrange
        viewModel.onAction(LoginAction.UpdatePassword("test1"))

        // Act
        viewModel.onAction(LoginAction.Login)

        // Assert
        assertEquals("Please enter email and password", viewModel.uiState.value.error)
        assertFalse(viewModel.uiState.value.isLoading)
    }

    @Test
    fun `Login with blank password shows validation error`() {
        // Arrange
        viewModel.onAction(LoginAction.UpdateEmail("john.doe@example.com"))

        // Act
        viewModel.onAction(LoginAction.Login)

        // Assert
        assertEquals("Please enter email and password", viewModel.uiState.value.error)
        assertFalse(viewModel.uiState.value.isLoading)
    }

    @Test
    fun `Login with both fields blank shows validation error`() {
        // Act
        viewModel.onAction(LoginAction.Login)

        // Assert
        assertEquals("Please enter email and password", viewModel.uiState.value.error)
    }

    @Test
    fun `Login success saves session and sets role`() = runTest {
        // Arrange
        viewModel.onAction(LoginAction.UpdateEmail("john.doe@example.com"))
        viewModel.onAction(LoginAction.UpdatePassword("test1"))

        // Act
        viewModel.onAction(LoginAction.Login)

        // Assert
        assertTrue(sessionState.isLoggedIn)
        assertEquals("admin", sessionState.role)
        assertNotNull(fakeAuth.getToken())
        assertEquals("john.doe@example.com", fakeAuth.getEmail())
    }

    @Test
    fun `Login success resets ui state to empty`() = runTest {
        // Arrange
        viewModel.onAction(LoginAction.UpdateEmail("john.doe@example.com"))
        viewModel.onAction(LoginAction.UpdatePassword("test1"))

        // Act
        viewModel.onAction(LoginAction.Login)

        // Assert
        assertEquals("", viewModel.uiState.value.email)
        assertEquals("", viewModel.uiState.value.password)
        assertNull(viewModel.uiState.value.error)
        assertFalse(viewModel.uiState.value.isLoading)
    }

    @Test
    fun `Login with wrong password shows error and does not log in`() = runTest {
        // Arrange
        viewModel.onAction(LoginAction.UpdateEmail("john.doe@example.com"))
        viewModel.onAction(LoginAction.UpdatePassword("wrongpassword"))

        // Act
        viewModel.onAction(LoginAction.Login)

        // Assert
        assertFalse(sessionState.isLoggedIn)
        assertEquals("Invalid email or password", viewModel.uiState.value.error)
        assertFalse(viewModel.uiState.value.isLoading)
    }

    @Test
    fun `Login with unknown email shows error`() = runTest {
        // Arrange
        viewModel.onAction(LoginAction.UpdateEmail("nobody@nowhere.com"))
        viewModel.onAction(LoginAction.UpdatePassword("test1"))

        // Act
        viewModel.onAction(LoginAction.Login)

        // Assert
        assertEquals("Invalid email or password", viewModel.uiState.value.error)
        assertFalse(sessionState.isLoggedIn)
    }

    @Test
    fun `Login trims leading and trailing spaces from email`() = runTest {
        // Arrange
        viewModel.onAction(LoginAction.UpdateEmail("  john.doe@example.com  "))
        viewModel.onAction(LoginAction.UpdatePassword("test1"))

        // Act
        viewModel.onAction(LoginAction.Login)

        // Assert
        assertTrue(sessionState.isLoggedIn)
    }
}
