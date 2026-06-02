package com.example.staffmanager.mockData

data class Comment(
    val id: String,
    val eventId: String,        // links comment to event
    val author: User,           // full User object, not just ID
    val content: String,
    val createdTime: String,
    val editedTime: String? = null   // null if never edited
)

val mockComments = listOf(
    Comment(
        id = "c1",
        eventId = "e1",
        author = mockUsers[0],
        content = "Looking forward to the picnic!",
        createdTime = "2024-07-10T09:00:00Z"
    ),
    Comment(
        id = "c2",
        eventId = "e1",
        author = mockUsers[1],
        content = "Will there be vegetarian options?",
        createdTime = "2024-07-11T14:30:00Z"
    ),
    Comment(
        id = "c3",
        eventId = "e2",
        author = mockUsers[2],
        content = "Great topic! Can we get the slides afterwards?",
        createdTime = "2024-08-15T10:00:00Z"
    )
)
