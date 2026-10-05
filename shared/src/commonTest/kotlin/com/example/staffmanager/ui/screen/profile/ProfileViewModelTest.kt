package com.example.staffmanager.ui.screen.profile

import com.example.staffmanager.ViewModelTest
import com.example.staffmanager.di.SessionState
import com.example.staffmanager.fakes.FakeAuthRepository
import com.example.staffmanager.repository.MockUserRepositoryImpl
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

class ProfileViewModelTest : ViewModelTest() {

    private fun createViewModel(
        storedEmail: String? = null,
        storedRole: String? = null
    ): Pair<ProfileViewModel, Pair<FakeAuthRepository, SessionState>> {
        val fakeAuth = FakeAuthRepository()
        if (storedEmail != null) {
            fakeAuth.saveSession("token", storedEmail, storedRole ?: "")
        }
        val sessionState = SessionState()
        val vm = ProfileViewModel(
            userRepository = MockUserRepositoryImpl(),
            authRepository = fakeAuth,
            sessionState = sessionState
        )
        return vm to (fakeAuth to sessionState)
    }

    @Test
    fun `loads user by stored email`() {
        // Arrange & Act
        val (vm, _) = createViewModel(storedEmail = "john.doe@example.com")

        // Assert
        assertNotNull(vm.uiState.value.user)
        assertEquals("john.doe@example.com", vm.uiState.value.user?.email)
        assertFalse(vm.uiState.value.isLoading)
    }

    @Test
    fun `falls back to getUserById(1) when no email stored`() {
        // Arrange & Act
        val (vm, _) = createViewModel(storedEmail = null)

        // Assert
        assertNotNull(vm.uiState.value.user)
        assertEquals(1, vm.uiState.value.user?.id)
    }

    @Test
    fun `user is null when stored email does not match any user`() {
        // Arrange & Act
        val (vm, _) = createViewModel(storedEmail = "ghost@nowhere.com")

        // Assert
        assertNull(vm.uiState.value.user)
        assertFalse(vm.uiState.value.isLoading)
    }

    @Test
    fun `Logout clears session token and email`() {
        // Arrange
        val (vm, pair) = createViewModel(storedEmail = "john.doe@example.com")
        val (fakeAuth, _) = pair

        // Act
        vm.onAction(ProfileAction.Logout)

        // Assert
        assertNull(fakeAuth.getToken())
        assertNull(fakeAuth.getEmail())
    }

    @Test
    fun `Logout sets isLoggedIn to false`() {
        // Arrange
        val (vm, pair) = createViewModel(storedEmail = "john.doe@example.com")
        val (_, sessionState) = pair
        sessionState.isLoggedIn = true

        // Act
        vm.onAction(ProfileAction.Logout)

        // Assert
        assertFalse(sessionState.isLoggedIn)
    }

    @Test
    fun `Logout clears role from sessionState`() {
        // Arrange
        val (vm, pair) = createViewModel(storedEmail = "john.doe@example.com", storedRole = "admin")
        val (_, sessionState) = pair
        sessionState.role = "admin"

        // Act
        vm.onAction(ProfileAction.Logout)

        // Assert
        assertNull(sessionState.role)
    }

    @Test
    fun `loads correct user name for Jane Smith`() {
        // Arrange & Act
        val (vm, _) = createViewModel(storedEmail = "jane.smith@example.com")

        // Assert
        assertEquals("Jane", vm.uiState.value.user?.firstName)
        assertEquals("Smith", vm.uiState.value.user?.lastName)
    }
}
