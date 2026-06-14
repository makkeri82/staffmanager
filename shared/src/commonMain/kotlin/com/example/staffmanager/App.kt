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
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.staffmanager.di.appModule
import com.example.staffmanager.ui.components.icons.menu
import com.example.staffmanager.ui.navigation.BottomNavBar
import com.example.staffmanager.ui.navigation.NavigationRoot
import com.example.staffmanager.ui.navigation.Navigator
import com.example.staffmanager.ui.navigation.Route
import com.example.staffmanager.ui.navigation.TOP_LEVEL_DESTINATIONS
import com.example.staffmanager.ui.navigation.rememberNavigationState
import com.example.staffmanager.ui.screen.drawer.DrawerAction
import com.example.staffmanager.ui.screen.drawer.DrawerScreen
import com.example.staffmanager.ui.screen.drawer.DrawerViewModel
import kotlinx.coroutines.launch
import org.koin.compose.KoinApplication
import org.koin.compose.viewmodel.koinViewModel
import org.koin.dsl.koinConfiguration

private const val APP_TITLE = "SESP"

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun App() {

    val navigationState = rememberNavigationState(
        startRoute = Route.Home,
        topLevelRoutes = TOP_LEVEL_DESTINATIONS.keys
    )
    val navigator = remember {
        Navigator(navigationState)
    }

    MaterialTheme {
        val drawerState = rememberDrawerState(DrawerValue.Closed)
        val scope = rememberCoroutineScope()

        val drawerViewModel: DrawerViewModel = koinViewModel()
        val drawerUiState by drawerViewModel.uiState.collectAsState()

        val currentRoute = navigationState.backStacks[navigationState.topLevelRoute]?.last()

        ModalNavigationDrawer(
            drawerState = drawerState,
            drawerContent = {
                DrawerScreen(
                    state = drawerUiState,
                    onAction = { action ->
                        when (action) {
                            DrawerAction.OpenProfile -> {
                                scope.launch { drawerState.close() }
                                navigator.navigate(Route.Profile)
                            }
                            DrawerAction.OpenAbout -> {
                                scope.launch { drawerState.close() }
                                // TODO: Navigate to AboutScreen when done
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
                        title = {
                            val title = when (currentRoute) {
                                is Route.EventDetails -> currentRoute.title
                                is Route.Profile -> "Profile"
                                else -> APP_TITLE
                            }
                            Text(title, color = Color.White)
                        },
                        navigationIcon = {
                            when (currentRoute) {
                                is Route.EventDetails, is Route.Profile -> IconButton(onClick = { navigator.goBack() }) {
                                    Icon(
                                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                        contentDescription = "Back",
                                        tint = Color.White
                                    )
                                }
                                else -> IconButton(onClick = { scope.launch { drawerState.open() } }) {
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
                bottomBar = {
                    if (currentRoute in TOP_LEVEL_DESTINATIONS) {
                        BottomNavBar(
                            selectedKey = navigationState.topLevelRoute,
                            onSelectKey = { navigator.navigate(it) }
                        )
                    }
                }
            ) { padding ->
                NavigationRoot(
                    navigationState = navigationState,
                    navigator = navigator,
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
