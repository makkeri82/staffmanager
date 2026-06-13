package com.example.staffmanager.ui.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
sealed interface Route : NavKey {

    @Serializable
    data object Home: Route, NavKey

    @Serializable
    data object Event: Route, NavKey

    @Serializable
    data class EventDetails(val eventId: String, val title: String): Route, NavKey
}