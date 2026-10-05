package com.example.staffmanager.ui.screen.login

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@RunWith(RobolectricTestRunner::class)
class LoginScreenTest {

    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun `renders EventsApp title`() {
        // Arrange & Act
        composeRule.setContent {
            LoginScreen(state = LoginUiState(), onAction = {})
        }

        // Assert
        composeRule.onNodeWithText("EventsApp").assertIsDisplayed()
    }

    @Test
    fun `renders Email field`() {
        // Arrange & Act
        composeRule.setContent {
            LoginScreen(state = LoginUiState(), onAction = {})
        }

        // Assert
        composeRule.onNodeWithText("Email").assertIsDisplayed()
    }

    @Test
    fun `renders Password field`() {
        // Arrange & Act
        composeRule.setContent {
            LoginScreen(state = LoginUiState(), onAction = {})
        }

        // Assert
        composeRule.onNodeWithText("Password").assertIsDisplayed()
    }

    @Test
    fun `renders Sign In button when not loading`() {
        // Arrange & Act
        composeRule.setContent {
            LoginScreen(state = LoginUiState(isLoading = false), onAction = {})
        }

        // Assert
        composeRule.onNodeWithText("Sign In").assertIsDisplayed()
    }

    @Test
    fun `hides Sign In text when loading`() {
        // Arrange & Act
        composeRule.setContent {
            LoginScreen(state = LoginUiState(isLoading = true), onAction = {})
        }

        // Assert
        composeRule.onNodeWithText("Sign In").assertDoesNotExist()
    }

    @Test
    fun `shows error message when error state is set`() {
        // Arrange & Act
        composeRule.setContent {
            LoginScreen(
                state = LoginUiState(error = "Invalid email or password"),
                onAction = {}
            )
        }

        // Assert
        composeRule.onNodeWithText("Invalid email or password").assertIsDisplayed()
    }

    @Test
    fun `does not show error message when error is null`() {
        // Arrange & Act
        composeRule.setContent {
            LoginScreen(state = LoginUiState(), onAction = {})
        }

        // Assert
        composeRule.onNodeWithText("Invalid email or password").assertDoesNotExist()
    }

    @Test
    fun `Sign In button click fires Login action`() {
        // Arrange
        var firedAction: LoginAction? = null
        composeRule.setContent {
            LoginScreen(state = LoginUiState(), onAction = { firedAction = it })
        }

        // Act
        composeRule.onNodeWithText("Sign In").performClick()

        // Assert
        assertTrue(firedAction is LoginAction.Login)
    }

    @Test
    fun `typing in Email field fires UpdateEmail action`() {
        // Arrange
        val actions = mutableListOf<LoginAction>()
        composeRule.setContent {
            LoginScreen(state = LoginUiState(), onAction = { actions.add(it) })
        }

        // Act
        composeRule.onNodeWithText("Email").performTextInput("test@example.com")

        // Assert
        assertTrue(actions.any { it is LoginAction.UpdateEmail })
    }

    @Test
    fun `UpdateEmail action carries typed value`() {
        // Arrange
        var lastAction: LoginAction? = null
        composeRule.setContent {
            LoginScreen(state = LoginUiState(), onAction = { lastAction = it })
        }

        // Act
        composeRule.onNodeWithText("Email").performTextInput("hello@world.com")

        // Assert
        assertEquals("hello@world.com", (lastAction as? LoginAction.UpdateEmail)?.value)
    }
}
