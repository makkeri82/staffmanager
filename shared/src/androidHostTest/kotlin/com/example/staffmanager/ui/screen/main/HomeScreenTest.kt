package com.example.staffmanager.ui.screen.main

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.example.staffmanager.mockData.mockEventsAttended
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import kotlin.test.assertEquals

@RunWith(RobolectricTestRunner::class)
class HomeScreenTest {

    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun `renders Attended section header`() {
        // Arrange & Act
        composeRule.setContent {
            HomeScreen(state = HomeUiState(), onEventClick = {})
        }

        // Assert
        composeRule.onNodeWithText("Attended").assertIsDisplayed()
    }

    @Test
    fun `renders attended event names`() {
        // Arrange & Act
        composeRule.setContent {
            HomeScreen(
                state = HomeUiState(events = mockEventsAttended),
                onEventClick = {}
            )
        }

        // Assert
        composeRule.onNodeWithText("Qstock 2026").assertIsDisplayed()
        composeRule.onNodeWithText("Viikinki festarit").assertIsDisplayed()
    }

    @Test
    fun `renders venue names`() {
        // Arrange & Act
        composeRule.setContent {
            HomeScreen(
                state = HomeUiState(events = mockEventsAttended),
                onEventClick = {}
            )
        }

        // Assert
        composeRule.onNodeWithText("Kuusisaari").assertIsDisplayed()
        composeRule.onNodeWithText("Burial grounds").assertIsDisplayed()
    }

    @Test
    fun `clicking attended event fires onEventClick with correct id`() {
        // Arrange
        var clickedId: String? = null
        composeRule.setContent {
            HomeScreen(
                state = HomeUiState(events = mockEventsAttended),
                onEventClick = { clickedId = it }
            )
        }

        // Act
        composeRule.onNodeWithText("Qstock 2026").performClick()

        // Assert
        assertEquals("e3", clickedId)
    }

    @Test
    fun `clicking second event fires correct id`() {
        // Arrange
        var clickedId: String? = null
        composeRule.setContent {
            HomeScreen(
                state = HomeUiState(events = mockEventsAttended),
                onEventClick = { clickedId = it }
            )
        }

        // Act
        composeRule.onNodeWithText("Viikinki festarit").performClick()

        // Assert
        assertEquals("e4", clickedId)
    }

    @Test
    fun `empty events list renders header without event cards`() {
        // Arrange & Act
        composeRule.setContent {
            HomeScreen(state = HomeUiState(events = emptyList()), onEventClick = {})
        }

        // Assert
        composeRule.onNodeWithText("Attended").assertIsDisplayed()
        assert(composeRule.onAllNodesWithText("Qstock 2026").fetchSemanticsNodes().isEmpty())
    }
}
