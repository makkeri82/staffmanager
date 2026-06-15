package com.example.staffmanager.repository

interface AuthRepository {
    fun saveSession(token: String, email: String, role: String)
    fun getToken(): String?
    fun getEmail(): String?
    fun getRole(): String?
    fun clearSession()
    fun isLoggedIn(): Boolean
}
