package com.example.staffmanager.repository

import com.example.staffmanager.mockData.User

interface UserRepository {
    suspend fun getUsers(): List<User>
    suspend fun getUserById(id: Int): User?
    suspend fun createUser(user: User): User
    suspend fun updateUser(user: User): User
    suspend fun deleteUser(id: Int): Boolean
    suspend fun login(email: String, password: String): User?
    suspend fun getRole(email: String): String?
}
