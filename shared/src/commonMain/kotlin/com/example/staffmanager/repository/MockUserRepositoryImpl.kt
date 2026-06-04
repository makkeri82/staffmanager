package com.example.staffmanager.repository

import com.example.staffmanager.mockData.User
import com.example.staffmanager.mockData.mockUsers

class MockUserRepositoryImpl : UserRepository {
    private val _users = mockUsers.toMutableList()

    override suspend fun getUsers(): List<User> {
        return _users.toList()
    }

    override suspend fun getUserById(id: Int): User? {
        return _users.find { it.id == id }
    }

    override suspend fun createUser(user: User): User {
        val newId = if (_users.isEmpty()) 1 else _users.maxOf { it.id } + 1
        val newUser = user.copy(id = newId)
        _users.add(newUser)
        return newUser
    }

    override suspend fun updateUser(user: User): User {
        val index = _users.indexOfFirst { it.id == user.id }
        if (index != -1) {
            _users[index] = user
            return user
        } else {
            throw IllegalArgumentException("User with id ${user.id} not found")
        }
    }

    override suspend fun deleteUser(id: Int): Boolean {
        return _users.removeAll { it.id == id }
    }
}
