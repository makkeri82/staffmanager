package com.example.staffmanager.ui.screen.chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.staffmanager.di.EventSelectionState
import com.example.staffmanager.mockData.Comment
import com.example.staffmanager.mockData.mockUsers
import com.example.staffmanager.repository.CommentRepository
import kotlin.random.Random
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class ChatUiState(
    val comments: List<Comment> = emptyList(),
    val isLoading: Boolean = true,
    val messageSent: Boolean = false
)

sealed interface ChatAction {
    data class AddComment(val content: String) : ChatAction
    data object MessageSentConsumed : ChatAction
}

class ChatViewModel(
    private val commentRepository: CommentRepository,
    private val selectionState: EventSelectionState
) : ViewModel() {
    private val _uiState = MutableStateFlow(ChatUiState())
    val uiState: StateFlow<ChatUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            val comments = commentRepository.getCommentsForEvent(selectionState.selectedEventId.value)
            _uiState.update { it.copy(comments = comments, isLoading = false) }
        }
    }

    fun onAction(action: ChatAction) {
        when (action) {
            is ChatAction.AddComment -> {
                if (action.content.isBlank()) return
                viewModelScope.launch {
                    val newComment = Comment(
                        id = "msg_${Random.nextInt(100_000)}",
                        eventId = selectionState.selectedEventId.value,
                        author = mockUsers[0],
                        content = action.content.trim(),
                        createdTime = "2026-06-14"
                    )
                    commentRepository.addComment(newComment)
                    _uiState.update {
                        it.copy(
                            comments = it.comments + newComment,
                            messageSent = true
                        )
                    }
                }
            }
            ChatAction.MessageSentConsumed -> {
                _uiState.update { it.copy(messageSent = false) }
            }
        }
    }
}
