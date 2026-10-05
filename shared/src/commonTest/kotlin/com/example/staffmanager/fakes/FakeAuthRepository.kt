package com.example.staffmanager.fakes

import com.example.staffmanager.repository.AuthRepository

class FakeAuthRepository : AuthRepository {
    private var token: String? = null
    private var email: String? = null
    private var role: String? = null

    override fun saveSession(token: String, email: String, role: String) {
        this.token = token
        this.email = email
        this.role = role
    }

    override fun getToken(): String? = token
    override fun getEmail(): String? = email
    override fun getRole(): String? = role

    override fun clearSession() {
        token = null
        email = null
        role = null
    }

    override fun isLoggedIn(): Boolean = token != null
}
