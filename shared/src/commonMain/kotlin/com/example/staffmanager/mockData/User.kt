package com.example.staffmanager.mockData

data class User(
    val id: Int,
    val firstName: String,
    val lastName: String,
    val email: String,
    val password: String,
    val phoneNumber: String,
    val jobTitle: String,
    val userLevel: String,
    val role: String,
    val profileImgUrl: String,
    val birthday: String,
    val additionalInfo: String,
    val createdAt: String
)

val mockUsers = listOf(
    User(
        id = 1,
        firstName = "John",
        lastName = "Doe",
        email = "john.doe@example.com",
        password = "test1",
        phoneNumber = "123-456-7890",
        jobTitle = "Software Engineer",
        userLevel = "admin",
        role = "Admin",
        profileImgUrl = "https://example.com/profiles/johndoe.jpg",
        birthday = "1990-01-01",
        additionalInfo = "Experienced in Kotlin and Android development.",
        createdAt = "2023-01-01T10:00:00Z"
    ),
    User(
        id = 2,
        firstName = "Jane",
        lastName = "Smith",
        email = "jane.smith@example.com",
        password = "test1",
        phoneNumber = "987-654-3210",
        jobTitle = "Product Manager",
        userLevel = "employee",
        role = "Security",
        profileImgUrl = "https://example.com/profiles/janesmith.jpg",
        birthday = "1992-05-15",
        additionalInfo = "Focused on user experience and product strategy.",
        createdAt = "2023-02-15T14:30:00Z"
    ),
    User(
        id = 3,
        firstName = "Alice",
        lastName = "Johnson",
        email = "alice.j@example.com",
        password = "test1",
        phoneNumber = "555-123-4567",
        jobTitle = "Security",
        userLevel = "employee",
        role = "Security",
        profileImgUrl = "https://example.com/profiles/alicej.jpg",
        birthday = "1995-11-20",
        additionalInfo = "Loves creating intuitive user interfaces.",
        createdAt = "2023-03-10T09:15:00Z"
    ),
    User(
        id = 4,
        firstName = "Pirkko",
        lastName = "Väätäinen",
        email = "prkko@example.com",
        password = "test1",
        phoneNumber = "045-555-4567",
        jobTitle = "Security",
        userLevel = "employee",
        role = "Security",
        profileImgUrl = "https://example.com/profiles/alicej.jpg",
        birthday = "1982-10-20",
        additionalInfo = "Loves creating intuitive user interfaces.",
        createdAt = "2023-03-10T09:15:00Z"
    )
)
