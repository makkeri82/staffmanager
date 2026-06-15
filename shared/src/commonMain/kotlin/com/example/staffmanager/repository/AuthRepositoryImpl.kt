package com.example.staffmanager.repository

import com.liftric.kvault.KVault

class AuthRepositoryImpl(private val vault: KVault) : AuthRepository {

    override fun saveSession(token: String, email: String, role: String) {
        vault.set("jwt_token", token)
        vault.set("user_email", email)
        vault.set("user_role", role)
    }

    override fun getToken(): String? = vault.string("jwt_token")

    override fun getEmail(): String? = vault.string("user_email")

    override fun getRole(): String? = vault.string("user_role")

    override fun clearSession() {
        vault.deleteObject("jwt_token")
        vault.deleteObject("user_email")
        vault.deleteObject("user_role")
    }

    override fun isLoggedIn(): Boolean = vault.string("jwt_token") != null
}
