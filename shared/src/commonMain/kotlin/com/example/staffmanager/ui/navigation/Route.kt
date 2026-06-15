package com.example.staffmanager.ui.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
sealed interface Route : NavKey {

    @Serializable
    data object Home: Route

    @Serializable
    data object Event: Route

    @Serializable
    data object Info: Route

    @Serializable
    data class EventDetails(val eventId: String, val title: String): Route

    @Serializable
    data object Profile: Route
}