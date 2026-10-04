package com.example.staffmanager.mockData

data class Event(
    val id: String,
    val eventName: String,
    val title: String,
    val description: String,
    val startDate: String,
    val endDate: String,
    val startTime: String,
    val endTime: String,
    val venueName: String,
    val location: String,
    val mapLinkUrl: String,
    val createdBy: String, // ID of the user who created the event
    val published: Boolean,
    val participants: List<User>,
    val comments: List<Comment>,
    val createdTime: String
)

val mockEventsUpcoming = listOf(
    Event(
        id = "e1",
        eventName = "Team Building",
        title = "Summer Picnic 2024",
        description = "A fun day out at the park with the whole team.",
        startDate = "2026-12-15T10:00:00Z",
        endDate = "2026-12-15T16:00:00Z",
        startTime = "10:00",
        endTime = "16:00",
        venueName = "Madison Sqr.",
        location = "Central Park, NY",
        mapLinkUrl = "https://maps.google.com/?q=Central+Park",
        createdBy = "1", // Created by John Doe
        published = true,
        participants = listOf(mockUsers[0], mockUsers[1]),
        comments = listOf(mockComments[0], mockComments[1]),
        createdTime = "2024-01-10T08:00:00Z"
    ),
    Event(
        id = "e2",
        eventName = "Workshop",
        title = "Android Development Best Practices",
        description = "Technical workshop on advanced Kotlin and Compose topics.",
        startDate = "2026-10-20T09:00:00Z",
        endDate = "2026-10-21T17:00:00Z",
        startTime = "09:00",
        endTime = "17:00",
        venueName = "Hallituskatu",
        location = "Office Meeting Room 3",
        mapLinkUrl = "https://maps.google.com/?q=Office+Meeting+Room+3",
        createdBy = "2", // Created by Jane Smith
        published = false,
        participants = listOf(mockUsers[1], mockUsers[2]),
        comments = listOf(mockComments[2]),
        createdTime = "2024-02-15T11:00:00Z"
    ))

val mockEventsAttended = listOf(
    Event(
        id = "e3",
        eventName = "Qstock 2026",
        title = "Qstock 2026",
        description = "",
        startDate = "2026-07-24T09:00:00Z",
        endDate = "2024-07-25T17:00:00Z",
        startTime = "09:00",
        endTime = "17:00",
        venueName = "Kuusisaari",
        location = "Oulu",
        mapLinkUrl = "https://maps.app.goo.gl/CEEQiK5w1EjNywrS7",
        createdBy = "2", // Created by Jane Smith
        published = false,
        participants = listOf(mockUsers[1], mockUsers[2]),
        comments = listOf(mockComments[2]),
        createdTime = "2024-02-15T11:00:00Z"
    ),
    Event(
        id = "e4",
        eventName = "Viikinki festarit",
        title = "Viikinki festarit",
        description = "mead, axes and heacy metal",
        startDate = "2026-08-10T09:00:00Z",
        endDate = "2024-08-12T17:00:00Z",
        startTime = "09:00",
        endTime = "17:00",
        venueName = "Burial grounds",
        location = "Oslo",
        mapLinkUrl = "https://maps.app.goo.gl/DZHg8MsF1t6U4HLS6",
        createdBy = "2", // Created by Jane Smith
        published = false,
        participants = listOf(mockUsers[1], mockUsers[2]),
        comments = listOf(mockComments[2]),
        createdTime = "2024-02-15T11:00:00Z"
    )
)
