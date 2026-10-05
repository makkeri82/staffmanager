package com.example.staffmanager.ui.screen.events

import com.example.staffmanager.ViewModelTest
import com.example.staffmanager.di.EventSelectionState
import com.example.staffmanager.di.SessionState
import com.example.staffmanager.repository.MockEventRepositoryImpl
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

class EventDetailsViewModelTest : ViewModelTest() {

    private fun createViewModel(
        eventId: String = "",
        role: String? = null
    ): EventDetailsViewModel {
        val selectionState = EventSelectionState().apply {
            selectedEventId.value = eventId
        }
        val sessionState = SessionState().apply {
            this.role = role
        }
        return EventDetailsViewModel(
            repository = MockEventRepositoryImpl(),
            selectionState = selectionState,
            sessionState = sessionState
        )
    }

    @Test
    fun `loads event matching selectedEventId`() {
        // Act
        val vm = createViewModel(eventId = "e1")

        // Assert
        assertNotNull(vm.uiState.value.event)
        assertEquals("e1", vm.uiState.value.event?.id)
    }

    @Test
    fun `loads correct event name`() {
        // Act
        val vm = createViewModel(eventId = "e1")

        // Assert
        assertEquals("Team Building", vm.uiState.value.event?.eventName)
    }

    @Test
    fun `loads event from attended list`() {
        // Act
        val vm = createViewModel(eventId = "e3")

        // Assert
        assertEquals("e3", vm.uiState.value.event?.id)
        assertEquals("Qstock 2026", vm.uiState.value.event?.eventName)
    }

    @Test
    fun `returns null event for unknown id`() {
        // Act
        val vm = createViewModel(eventId = "nonexistent")

        // Assert
        assertNull(vm.uiState.value.event)
    }

    @Test
    fun `isAdmin is true when session role is admin`() {
        // Act
        val vm = createViewModel(eventId = "e1", role = "admin")

        // Assert
        assertTrue(vm.uiState.value.isAdmin)
    }

    @Test
    fun `isAdmin is false when session role is employee`() {
        // Act
        val vm = createViewModel(eventId = "e1", role = "employee")

        // Assert
        assertFalse(vm.uiState.value.isAdmin)
    }

    @Test
    fun `isAdmin is false when session role is null`() {
        // Act
        val vm = createViewModel(eventId = "e1", role = null)

        // Assert
        assertFalse(vm.uiState.value.isAdmin)
    }
}
