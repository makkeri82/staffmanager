package com.example.staffmanager

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.staffmanager.di.appModule
import com.example.staffmanager.ui.components.icons.menu
import com.example.staffmanager.ui.navigation.BottomNavBar
import com.example.staffmanager.ui.screen.drawer.DrawerAction
import com.example.staffmanager.ui.screen.drawer.DrawerScreen
import com.example.staffmanager.ui.screen.drawer.DrawerViewModel
import com.example.staffmanager.ui.screen.events.EventDetailsAction
import com.example.staffmanager.ui.screen.events.EventDetailsScreen
import com.example.staffmanager.ui.screen.events.EventDetailsViewModel
import com.example.staffmanager.ui.screen.events.EventsAction
import com.example.staffmanager.ui.screen.events.EventsScreen
import com.example.staffmanager.ui.screen.events.EventsViewModel
import com.example.staffmanager.ui.screen.main.HomeScreen
import com.example.staffmanager.ui.screen.main.HomeViewModel
import com.example.staffmanager.ui.screen.profile.ProfileAction
import com.example.staffmanager.ui.screen.profile.ProfileScreen
import com.example.staffmanager.ui.screen.profile.ProfileViewModel
import kotlinx.coroutines.launch
import org.koin.compose.KoinApplication
import org.koin.compose.viewmodel.koinViewModel
import org.koin.dsl.koinConfiguration

@Composable
fun App() {
    MaterialTheme {
        val navController = rememberNavController()
        val drawerState = rememberDrawerState(DrawerValue.Closed)
        val scope = rememberCoroutineScope()

        val drawerViewModel: DrawerViewModel = koinViewModel()
        val drawerUiState by drawerViewModel.uiState.collectAsState()

        ModalNavigationDrawer(
            drawerState = drawerState,
            drawerContent = {
                DrawerScreen(
                    state = drawerUiState,
                    onAction = { action ->
                        when (action) {
                            DrawerAction.OpenProfile -> {
                                scope.launch { drawerState.close() }
                                navController.navigate("profile")
                            }
                            DrawerAction.OpenAbout -> {
                                scope.launch { drawerState.close() }
                            }
                        }
                    }
                )
            }
        ) {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = { Text("SESP", color = Color.White) },
                        navigationIcon = {
                            IconButton(onClick = { scope.launch { drawerState.open() } }) {
                                Icon(
                                    imageVector = menu,
                                    contentDescription = "Menu",
                                    tint = Color.White
                                )
                            }
                        },
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = Color.Black
                        )
                    )
                },
                bottomBar = { BottomNavBar(navController) }
            ) { padding ->
                NavHost(
                    navController = navController,
                    startDestination = "home",
                    modifier = Modifier.padding(padding)
                ) {
                    composable("home") {
                        val viewModel: HomeViewModel = koinViewModel()
                        val state by viewModel.uiState.collectAsState()
                        HomeScreen(
                            state = state,
                            onAction = viewModel::onAction
                        )
                    }
                    composable("events") {
                        val viewModel: EventsViewModel = koinViewModel()
                        val state by viewModel.uiState.collectAsState()
                        EventsScreen(
                            state = state,
                            onAction = { action ->
                                when (action) {
                                    EventsAction.NavigateToEventDetails -> navController.navigate("event_details")
                                    else -> viewModel.onAction(action)
                                }
                            }
                        )
                    }
                    composable("event_details") {
                        val viewModel: EventDetailsViewModel = koinViewModel()
                        val state by viewModel.uiState.collectAsState()
                        EventDetailsScreen(
                            state = state,
                            onAction = { action ->
                                when (action) {
                                    EventDetailsAction.NavigateBack -> navController.navigateUp()
                                }
                            }
                        )
                    }
                    composable("profile") {
                        val viewModel: ProfileViewModel = koinViewModel()
                        val state by viewModel.uiState.collectAsState()
                        ProfileScreen(
                            state = state,
                            onAction = { action ->
                                when (action) {
                                    ProfileAction.NavigateBack -> navController.navigateUp()
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
@Preview
fun PreviewApp() {
    KoinApplication(
        configuration = koinConfiguration(declaration = { modules(appModule) }),
        content = {
            MaterialTheme {
                App()
            }
        })
}
