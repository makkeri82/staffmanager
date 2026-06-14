package com.example.staffmanager.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.ui.graphics.vector.ImageVector

data class BottomNavItem(
    val title: String,
    val icon: ImageVector
)

val TOP_LEVEL_DESTINATIONS = mapOf(
    Route.Home to BottomNavItem(
        icon = Icons.Default.Home,
        title = "Home"
    ),
    Route.Event to BottomNavItem(
        icon = Icons.Default.Event,
        title = "Events"
    ),
    Route.Info to BottomNavItem(
        icon = Icons.Default.Info,
        title = "Info"
    )
)
