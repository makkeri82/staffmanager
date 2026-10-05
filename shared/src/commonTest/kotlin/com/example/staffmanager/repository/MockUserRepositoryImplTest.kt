package com.example.staffmanager.repository

import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

class MockUserRepositoryImplTest {

    private val repo = MockUserRepositoryImpl()

    @Test
    fun `login returns user for correct credentials`() = runTest {
        // Act
        val user = repo.login("john.doe@example.com", "test1")

        // Assert
        assertNotNull(user)
        assertEquals(1, user.id)
        assertEquals("John", user.firstName)
    }

    @Test
    fun `login returns null for wrong password`() = runTest {
        // Act
        val user = repo.login("john.doe@example.com", "wrongpassword")

        // Assert
        assertNull(user)
    }

    @Test
    fun `login returns null for unknown email`() = runTest {
        // Act
        val user = repo.login("nobody@example.com", "test1")

        // Assert
        assertNull(user)
    }

    @Test
    fun `getUserById returns correct user`() = runTest {
        // Act
        val user = repo.getUserById(1)

        // Assert
        assertNotNull(user)
        assertEquals(1, user.id)
        assertEquals("john.doe@example.com", user.email)
    }

    @Test
    fun `getUserById returns null for missing id`() = runTest {
        // Act
        val user = repo.getUserById(999)

        // Assert
        assertNull(user)
    }

    @Test
    fun `getUsers returns all four mock users`() = runTest {
        // Act
        val users = repo.getUsers()

        // Assert
        assertEquals(4, users.size)
    }
}
