package com.example.staffmanager.ui.screen.events

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.performClick
import com.example.staffmanager.mockData.mockEventsUpcoming
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import kotlin.test.assertEquals

@RunWith(RobolectricTestRunner::class)
class EventsScreenTest {

    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun `renders Pending section header`() {
        // Arrange & Act
        composeRule.setContent {
            EventsScreen(state = EventsUiState(), onEventClick = {})
        }

        // Assert
        composeRule.onNodeWithText("Pending").assertIsDisplayed()
    }

    @Test
    fun `renders event names from state`() {
        // Arrange & Act
        composeRule.setContent {
            EventsScreen(
                state = EventsUiState(events = mockEventsUpcoming),
                onEventClick = {}
            )
        }

        // Assert
        composeRule.onNodeWithText("Team Building").assertIsDisplayed()
        composeRule.onNodeWithText("Workshop").assertIsDisplayed()
    }

    @Test
    fun `renders venue names`() {
        // Arrange & Act
        composeRule.setContent {
            EventsScreen(
                state = EventsUiState(events = mockEventsUpcoming),
                onEventClick = {}
            )
        }

        // Assert
        composeRule.onNodeWithText("Madison Sqr.").assertIsDisplayed()
        composeRule.onNodeWithText("Hallituskatu").assertIsDisplayed()
    }

    @Test
    fun `clicking event card fires onEventClick with correct id`() {
        // Arrange
        var clickedId: String? = null
        composeRule.setContent {
            EventsScreen(
                state = EventsUiState(events = mockEventsUpcoming),
                onEventClick = { clickedId = it }
            )
        }

        // Act
        composeRule.onNodeWithText("Team Building").performClick()

        // Assert
        assertEquals("e1", clickedId)
    }

    @Test
    fun `clicking second event fires correct id`() {
        // Arrange
        var clickedId: String? = null
        composeRule.setContent {
            EventsScreen(
                state = EventsUiState(events = mockEventsUpcoming),
                onEventClick = { clickedId = it }
            )
        }

        // Act
        composeRule.onNodeWithText("Workshop").performClick()

        // Assert
        assertEquals("e2", clickedId)
    }

    @Test
    fun `empty events list renders header without event cards`() {
        // Arrange & Act
        composeRule.setContent {
            EventsScreen(state = EventsUiState(events = emptyList()), onEventClick = {})
        }

        // Assert
        composeRule.onNodeWithText("Pending").assertIsDisplayed()
        assert(composeRule.onAllNodesWithText("Team Building").fetchSemanticsNodes().isEmpty())
    }
}
