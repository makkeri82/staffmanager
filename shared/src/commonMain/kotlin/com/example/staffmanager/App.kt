package com.example.staffmanager

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.DrawerValue
import androidx.compose.ui.ExperimentalComposeUiApi
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
import androidx.navigation.compose.rememberNavController
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.savedstate.serialization.SavedStateConfiguration
import com.example.staffmanager.di.appModule
import com.example.staffmanager.ui.components.icons.menu
import com.example.staffmanager.ui.navigation.BottomNavBar
import com.example.staffmanager.ui.navigation.NavigationRoot
import com.example.staffmanager.ui.navigation.Route
import com.example.staffmanager.ui.screen.drawer.DrawerAction
import com.example.staffmanager.ui.screen.drawer.DrawerScreen
import com.example.staffmanager.ui.screen.drawer.DrawerViewModel
import kotlinx.coroutines.launch
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import org.koin.compose.KoinApplication
import org.koin.compose.viewmodel.koinViewModel
import org.koin.dsl.koinConfiguration

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun App() {

    val backStack = rememberNavBackStack(
        configuration = SavedStateConfiguration {
            serializersModule = SerializersModule {
                polymorphic(NavKey::class) {
                    subclass(Route.Home::class, Route.Home.serializer())
                    subclass(Route.Event::class, Route.Event.serializer())
                    subclass(Route.EventDetails::class, Route.EventDetails.serializer())
                }
            }
        },
        Route.Event
    )
    val currentRoute = backStack.lastOrNull()

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
                            DrawerAction.NavigateBack -> { scope.launch { drawerState.close() }}
                        }
                    }
                )
            }
        ) {
            Scaffold(
                topBar = {
                    TopAppBar(
                        // title = { Text("SESP", color = Color.White) },
                        title = {
                            val title = when (currentRoute) {
                                is Route.Home -> "SESP"
                                is Route.Event -> "Events"
                                is Route.EventDetails -> currentRoute.title
                                else -> "SESP"
                            }
                            Text(title, color = Color.White)
                        },
                        navigationIcon = {
                            if (backStack.size > 1) {
                                IconButton(onClick = { backStack.removeLastOrNull() }) {
                                    Icon(
                                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                        contentDescription = "Back",
                                        tint = Color.White
                                    )
                                }
                            } else {
                                IconButton(onClick = { scope.launch { drawerState.open() } }) {
                                    Icon(
                                        imageVector = menu,
                                        contentDescription = "Menu",
                                        tint = Color.White
                                    )
                                }
                            }
                        },
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = Color.Black
                        )
                    )
                },
                bottomBar = { BottomNavBar(navController) }
            ) { padding ->
                NavigationRoot(
                    backStack = backStack,
                    modifier = Modifier.padding(padding)
                )
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
