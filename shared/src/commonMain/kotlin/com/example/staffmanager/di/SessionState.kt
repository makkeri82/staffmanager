package com.example.staffmanager.di

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class SessionState {
    var isLoggedIn: Boolean by mutableStateOf(false)
    var role: String? by mutableStateOf(null)
}
