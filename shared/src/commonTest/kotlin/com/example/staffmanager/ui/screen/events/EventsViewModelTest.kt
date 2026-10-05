package com.example.staffmanager.ui.screen.events

import com.example.staffmanager.ViewModelTest
import com.example.staffmanager.di.EventSelectionState
import com.example.staffmanager.repository.MockEventRepositoryImpl
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class EventsViewModelTest : ViewModelTest() {

    private lateinit var viewModel: EventsViewModel
    private lateinit var selectionState: EventSelectionState

    @BeforeTest
    fun setUp() {
        selectionState = EventSelectionState()
        viewModel = EventsViewModel(
            eventRepository = MockEventRepositoryImpl(),
            selectionState = selectionState
        )
    }

    @Test
    fun `init loads upcoming events`() {
        // Assert
        val events = viewModel.uiState.value.events
        assertEquals(2, events.size)
    }

    @Test
    fun `init loads events with correct ids`() {
        // Assert
        val ids = viewModel.uiState.value.events.map { it.id }
        assertTrue("e1" in ids)
        assertTrue("e2" in ids)
    }

    @Test
    fun `init loads events with correct names`() {
        // Assert
        val names = viewModel.uiState.value.events.map { it.eventName }
        assertTrue("Team Building" in names)
        assertTrue("Workshop" in names)
    }

    @Test
    fun `SelectEvent updates selectedEventId in selectionState`() {
        // Act
        viewModel.onAction(EventsAction.SelectEvent("e1"))

        // Assert
        assertEquals("e1", selectionState.selectedEventId.value)
    }

    @Test
    fun `SelectEvent with second event updates selectionState correctly`() {
        // Act
        viewModel.onAction(EventsAction.SelectEvent("e2"))

        // Assert
        assertEquals("e2", selectionState.selectedEventId.value)
    }

    @Test
    fun `SelectEvent overwrites previous selection`() {
        // Arrange
        viewModel.onAction(EventsAction.SelectEvent("e1"))

        // Act
        viewModel.onAction(EventsAction.SelectEvent("e2"))

        // Assert
        assertEquals("e2", selectionState.selectedEventId.value)
    }
}
