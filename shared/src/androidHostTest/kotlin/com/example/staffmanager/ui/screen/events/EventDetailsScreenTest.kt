package com.example.staffmanager.ui.screen.events

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performScrollTo
import com.example.staffmanager.mockData.mockEventsUpcoming
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class EventDetailsScreenTest {

    @get:Rule
    val composeRule = createComposeRule()

    // e1: eventName="Team Building", title="Summer Picnic 2024",
    //     location="Central Park, NY", participants=[John Doe, Jane Smith]
    private val testEvent = mockEventsUpcoming.first()

    @Test
    fun `renders event name as headline`() {
        // Arrange & Act
        composeRule.setContent {
            EventDetailsScreen(state = EventDetailsUiState(event = testEvent))
        }

        // Assert
        composeRule.onNodeWithText("Team Building").assertIsDisplayed()
    }

    @Test
    fun `renders event title`() {
        // Arrange & Act
        composeRule.setContent {
            EventDetailsScreen(state = EventDetailsUiState(event = testEvent))
        }

        // Assert
        composeRule.onNodeWithText("Summer Picnic 2024").assertIsDisplayed()
    }

    @Test
    fun `renders Date and Time section`() {
        // Arrange & Act
        composeRule.setContent {
            EventDetailsScreen(state = EventDetailsUiState(event = testEvent))
        }

        // Assert
        composeRule.onNodeWithText("Date & Time").assertIsDisplayed()
    }

    @Test
    fun `renders Location section header`() {
        // Arrange & Act
        composeRule.setContent {
            EventDetailsScreen(state = EventDetailsUiState(event = testEvent))
        }

        // Assert
        composeRule.onNodeWithText("Location").assertIsDisplayed()
    }

    @Test
    fun `renders event location value`() {
        // Arrange & Act
        composeRule.setContent {
            EventDetailsScreen(state = EventDetailsUiState(event = testEvent))
        }

        // Assert
        composeRule.onNodeWithText("Central Park, NY").assertIsDisplayed()
    }

    @Test
    fun `shows Participants section for admin`() {
        // Arrange & Act
        composeRule.setContent {
            EventDetailsScreen(state = EventDetailsUiState(event = testEvent, isAdmin = true))
        }

        // Assert — participants are below the fold; scroll to bring them into view
        composeRule.onNodeWithText("Participants (2)").performScrollTo().assertIsDisplayed()
    }

    @Test
    fun `shows participant names for admin`() {
        // Arrange & Act
        composeRule.setContent {
            EventDetailsScreen(state = EventDetailsUiState(event = testEvent, isAdmin = true))
        }

        // Assert — participants are below the fold; scroll to bring them into view
        composeRule.onNodeWithText("John Doe").performScrollTo().assertIsDisplayed()
        composeRule.onNodeWithText("Jane Smith").performScrollTo().assertIsDisplayed()
    }

    @Test
    fun `hides Participants section for non-admin`() {
        // Arrange & Act
        composeRule.setContent {
            EventDetailsScreen(state = EventDetailsUiState(event = testEvent, isAdmin = false))
        }

        // Assert
        assert(composeRule.onAllNodesWithText("Participants (2)").fetchSemanticsNodes().isEmpty())
    }

    @Test
    fun `renders nothing when event is null`() {
        // Arrange & Act
        composeRule.setContent {
            EventDetailsScreen(state = EventDetailsUiState(event = null))
        }

        // Assert
        assert(composeRule.onAllNodesWithText("Team Building").fetchSemanticsNodes().isEmpty())
        assert(composeRule.onAllNodesWithText("Location").fetchSemanticsNodes().isEmpty())
    }
}
