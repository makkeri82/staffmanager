package com.example.staffmanager.repository

import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

class MockEventRepositoryImplTest {

    private val repo = MockEventRepositoryImpl()

    @Test
    fun `getEvents returns all upcoming events`() = runTest {
        // Act
        val events = repo.getEvents()

        // Assert
        assertEquals(2, events.size)
    }

    @Test
    fun `getEvents returns events with correct ids`() = runTest {
        // Act
        val ids = repo.getEvents().map { it.id }

        // Assert
        assertTrue("e1" in ids)
        assertTrue("e2" in ids)
    }

    @Test
    fun `getAttendedEvents returns all attended events`() = runTest {
        // Act
        val events = repo.getAttendedEvents()

        // Assert
        assertEquals(2, events.size)
    }

    @Test
    fun `getAttendedEvents returns events with correct ids`() = runTest {
        // Act
        val ids = repo.getAttendedEvents().map { it.id }

        // Assert
        assertTrue("e3" in ids)
        assertTrue("e4" in ids)
    }

    @Test
    fun `getEventById finds event from upcoming list`() = runTest {
        // Act
        val event = repo.getEventById("e1")

        // Assert
        assertNotNull(event)
        assertEquals("e1", event.id)
        assertEquals("Team Building", event.eventName)
    }

    @Test
    fun `getEventById finds event from attended list`() = runTest {
        // Act
        val event = repo.getEventById("e3")

        // Assert
        assertNotNull(event)
        assertEquals("e3", event.id)
    }

    @Test
    fun `getEventById returns null for unknown id`() = runTest {
        // Act
        val event = repo.getEventById("x999")

        // Assert
        assertNull(event)
    }
}
