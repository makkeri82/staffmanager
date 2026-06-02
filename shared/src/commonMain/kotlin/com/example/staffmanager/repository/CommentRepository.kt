package com.example.staffmanager.repository

import com.example.staffmanager.mockData.Comment

interface CommentRepository {
    suspend fun getCommentsForEvent(eventId: String): List<Comment>
    suspend fun addComment(comment: Comment)
    suspend fun editComment(commentId: String, newContent: String): Comment
    suspend fun deleteComment(commentId: String)
}
