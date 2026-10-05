package com.example.staffmanager.ui.screen.chat

import com.example.staffmanager.ViewModelTest
import com.example.staffmanager.di.EventSelectionState
import com.example.staffmanager.repository.MockCommentRepositoryImpl
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class ChatViewModelTest : ViewModelTest() {

    private fun createViewModel(eventId: String): ChatViewModel {
        val selectionState = EventSelectionState().apply {
            selectedEventId.value = eventId
        }
        return ChatViewModel(
            commentRepository = MockCommentRepositoryImpl(),
            selectionState = selectionState
        )
    }

    @Test
    fun `init loads comments for event with two comments`() {
        // Act — e1 has mockComments c1 and c2
        val vm = createViewModel(eventId = "e1")

        // Assert
        assertEquals(2, vm.uiState.value.comments.size)
        assertFalse(vm.uiState.value.isLoading)
    }

    @Test
    fun `init loads comments for event with one comment`() {
        // Act — e2 has mockComment c3
        val vm = createViewModel(eventId = "e2")

        // Assert
        assertEquals(1, vm.uiState.value.comments.size)
    }

    @Test
    fun `init returns empty list for event with no comments`() {
        // Act — e3 has no comments in mockComments
        val vm = createViewModel(eventId = "e3")

        // Assert
        assertTrue(vm.uiState.value.comments.isEmpty())
        assertFalse(vm.uiState.value.isLoading)
    }

    @Test
    fun `AddComment appends comment and sets messageSent`() = runTest {
        // Arrange
        val vm = createViewModel(eventId = "e1")
        val initialCount = vm.uiState.value.comments.size

        // Act
        vm.onAction(ChatAction.AddComment("Hello everyone!"))

        // Assert
        assertEquals(initialCount + 1, vm.uiState.value.comments.size)
        assertTrue(vm.uiState.value.messageSent)
    }

    @Test
    fun `AddComment stores trimmed content`() = runTest {
        // Arrange
        val vm = createViewModel(eventId = "e1")

        // Act
        vm.onAction(ChatAction.AddComment("  padded content  "))

        // Assert
        assertEquals("padded content", vm.uiState.value.comments.last().content)
    }

    @Test
    fun `AddComment stores correct eventId`() = runTest {
        // Arrange
        val vm = createViewModel(eventId = "e1")

        // Act
        vm.onAction(ChatAction.AddComment("Test message"))

        // Assert
        assertEquals("e1", vm.uiState.value.comments.last().eventId)
    }

    @Test
    fun `AddComment with blank content is ignored`() = runTest {
        // Arrange
        val vm = createViewModel(eventId = "e1")
        val initialCount = vm.uiState.value.comments.size

        // Act
        vm.onAction(ChatAction.AddComment("   "))

        // Assert
        assertEquals(initialCount, vm.uiState.value.comments.size)
        assertFalse(vm.uiState.value.messageSent)
    }

    @Test
    fun `MessageSentConsumed resets messageSent to false`() = runTest {
        // Arrange
        val vm = createViewModel(eventId = "e1")
        vm.onAction(ChatAction.AddComment("A message"))
        assertTrue(vm.uiState.value.messageSent)

        // Act
        vm.onAction(ChatAction.MessageSentConsumed)

        // Assert
        assertFalse(vm.uiState.value.messageSent)
    }
}
