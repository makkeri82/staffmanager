package com.example.staffmanager.ui.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

private val navItems = listOf(
    BottomNavItem("HOME", "home", Icons.Default.Home),
    BottomNavItem("EVENTS", "events", Icons.Default.Event),
    BottomNavItem("INFO", "info", Icons.Default.Info)
)

@Composable
fun BottomNavBar(navController: NavController) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route

    NavigationBar(
        modifier = Modifier
            .fillMaxWidth(),
        containerColor = Color.Black,
        contentColor = Color.Black,
    ) {
        navItems.forEach { item ->
            NavigationBarItem(
                selected = currentRoute == item.route,
                onClick = {
                    if (currentRoute != item.route && item.route != "info") {
                        navController.navigate(item.route) {
                            popUpTo("home") { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                },
                label = { Text(item.title) },
                icon = {
                    item.icon?.let {
                        Icon(it, contentDescription = item.title)
                    }
                },
                alwaysShowLabel = false,
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color.White,
                    selectedTextColor = Color.White,
                    unselectedIconColor = Color.Gray,
                    unselectedTextColor = Color.Gray,
                    indicatorColor = Color.Black
                )
            )
        }
    }
}

@Preview
@Composable
fun BottomNavBarPreview() {
    val navController = rememberNavController()
    BottomNavBar(navController = navController)
}
