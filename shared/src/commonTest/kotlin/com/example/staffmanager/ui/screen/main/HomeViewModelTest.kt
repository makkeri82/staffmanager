package com.example.staffmanager.ui.screen.main

import com.example.staffmanager.ViewModelTest
import com.example.staffmanager.di.EventSelectionState
import com.example.staffmanager.repository.MockEventRepositoryImpl
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class HomeViewModelTest : ViewModelTest() {

    private lateinit var viewModel: HomeViewModel
    private lateinit var selectionState: EventSelectionState

    @BeforeTest
    fun setUp() {
        selectionState = EventSelectionState()
        viewModel = HomeViewModel(
            eventRepository = MockEventRepositoryImpl(),
            selectionState = selectionState
        )
    }

    @Test
    fun `init loads attended events`() {
        // Assert
        assertEquals(2, viewModel.uiState.value.events.size)
    }

    @Test
    fun `init loads attended events with correct ids`() {
        // Assert
        val ids = viewModel.uiState.value.events.map { it.id }
        assertTrue("e3" in ids)
        assertTrue("e4" in ids)
    }

    @Test
    fun `init loads attended events with correct names`() {
        // Assert
        val names = viewModel.uiState.value.events.map { it.eventName }
        assertTrue("Qstock 2026" in names)
        assertTrue("Viikinki festarit" in names)
    }

    @Test
    fun `SelectEvent updates selectedEventId in selectionState`() {
        // Act
        viewModel.onAction(HomeAction.SelectEvent("e3"))

        // Assert
        assertEquals("e3", selectionState.selectedEventId.value)
    }

    @Test
    fun `SelectEvent overwrites previous selection`() {
        // Arrange
        viewModel.onAction(HomeAction.SelectEvent("e3"))

        // Act
        viewModel.onAction(HomeAction.SelectEvent("e4"))

        // Assert
        assertEquals("e4", selectionState.selectedEventId.value)
    }
}
