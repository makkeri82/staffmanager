package com.example.staffmanager.repository

import com.example.staffmanager.mockData.Comment
import com.example.staffmanager.mockData.mockComments
import kotlinx.coroutines.delay
import kotlin.time.Clock

class MockCommentRepositoryImpl : CommentRepository {
    private val _comments = mockComments.toMutableList()

    override suspend fun getCommentsForEvent(eventId: String): List<Comment> {
        return _comments.filter { it.eventId == eventId }
    }

    override suspend fun addComment(comment: Comment) {
        _comments.add(comment)
    }

    override suspend fun editComment(commentId: String, newContent: String): Comment {
        delay(150)
        val index = _comments.indexOfFirst { it.id == commentId }
        if (index == -1) error("Comment not found: $commentId")
        val updated = _comments[index].copy(
            content = newContent,
            editedTime = Clock.System.now().toString()
        )
        _comments[index] = updated
        return updated
    }

    override suspend fun deleteComment(commentId: String) {
        _comments.removeAll { it.id == commentId }
    }
}
